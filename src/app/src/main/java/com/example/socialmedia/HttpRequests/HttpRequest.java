package com.example.socialmedia.HttpRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpRequest {

    HashMap<String, String> params = new HashMap<>();
    HashMap<String, File> files = new HashMap<>();

    private String method;
    private String requestUrl;
    private String charset;

    private String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private PrintWriter writer;

    // Construtor da classe HttpRequest
    // requestUrl -> o endereco a ser conectado
    // method -> metodo HTTP a ser usado. Pode ser GET ou POST
    // charset -> codificacao de caracteres usada na troca de dados. Geralmente UTF-8
    public HttpRequest(String requestUrl, String method, String charset) {
        this.method = method;
        this.requestUrl = requestUrl;
        this.charset = charset;
    }

    // Adiciona um parametro a ser enviado ao servidor. Funciona com conexoes GET e POST.
    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    // Adiciona um arquivo a ser enviado ao servidor. Funciona apenas com conexoes POST.
    public void addFile(String key, File file) {
        this.files.put(key, file);
    }

    // Executa a conexao: anexa os parametros e arquivos a serem enviados ao servidor, abre uma
    // conexao com o servidor. Ao final, retorna a resposta do servidor na forma de um InputStream,
    // o qual deve ser convertido para o dado originalmente enviado.
    public InputStream execute() throws IOException {
        if(method == "GET") {
            requestUrl = requestUrl + "?";
            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                requestUrl = requestUrl + URLEncoder.encode(pair.getKey().toString(),"UTF-8");
                requestUrl = requestUrl + "=";
                requestUrl = requestUrl + URLEncoder.encode(pair.getValue().toString(),"UTF-8");
                requestUrl = requestUrl + "&";
            }
            requestUrl =  requestUrl.substring(0, requestUrl.length() - 1);
        }

        URL url = new URL(this.requestUrl);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoInput(true);

        // Timeout for reading InputStream arbitrarily set to 3000ms.
        httpConn.setReadTimeout(3000);
        // Timeout for connection.connect() arbitrarily set to 3000ms.
        httpConn.setConnectTimeout(3000);
        // GET or POST
        httpConn.setRequestMethod(method);

        if(method == "POST") {
            boundary = "===" + System.currentTimeMillis() + "===";
            httpConn.setDoOutput(true);    // indicates POST method
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);

            Iterator itParams = params.entrySet().iterator();
            while (itParams.hasNext()) {
                Map.Entry pair = (Map.Entry) itParams.next();
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + pair.getKey().toString() + "\"")
                        .append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=" + charset).append(
                        LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(pair.getValue().toString()).append(LINE_FEED);
                writer.flush();
            }

            Iterator itFiles = files.entrySet().iterator();
            while (itFiles.hasNext()) {
                Map.Entry pair = (Map.Entry) itFiles.next();
                File file = (File) pair.getValue();
                String fileName = file.getName();
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append(
                        "Content-Disposition: form-data; name=\"" + pair.getKey().toString()
                                + "\"; filename=\"" + fileName + "\"")
                        .append(LINE_FEED);
                writer.append(
                        "Content-Type: "
                                + URLConnection.guessContentTypeFromName(fileName))
                        .append(LINE_FEED);
                writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();
                writer.append(LINE_FEED);
                writer.flush();
            }

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
        }

        int responseCode = httpConn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }
        // Retrieve the response body as an InputStream.
        return httpConn.getInputStream();
    }

    // Fecha a conexao
    public void finish() throws IOException {
        InputStream inputStream = httpConn.getInputStream();
        if (inputStream != null) {
            inputStream.close();
        }
        if (httpConn != null) {
            httpConn.disconnect();
        }
    }

    // Retorna o resultado do input stream em uma string
    public String getResponseString(InputStream inputStream, String charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
        StringBuilder builder = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }

        inputStream.close();

        return builder.toString();
    }

    // Retorna um objeto complexo pegando campos comuns da resposta proveniente do servidor
    public <TObject> ObjectResponse<TObject> getCommonObject(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        int status = jsonObject.getInt("status");
        String message = jsonObject.getString("message");
        boolean success = status == 0;

        return new ObjectResponse<TObject>(jsonObject, success, message);
    }
}