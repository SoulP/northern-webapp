package cloud.northern.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Utility
 *
 * @author SoulP
 *
 */
public class Utility {
    private static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private Utility() {
    }

    /**
     * String to Base64
     *
     * @param str
     * @return Base64
     */
    public static String encodeBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * Base64 to String
     *
     * @param base64
     * @return String
     */
    public static String decodeBase64(String base64) {
        return new String(Base64.getDecoder().decode(base64));
    }

    /**
     * JSON to Object
     *
     * @param json
     *            JSON
     * @param t
     *            Object class
     * @return Object
     */
    public static <T> T json2obj(String json, Class<T> t) {
        try {
            return mapper.readValue(json, t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object to JSON
     *
     * @param t
     *            Object
     * @return JSON
     */
    public static <T> String obj2json(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP GET
     *
     * @param strUrl
     *            URL address
     * @return response data (JSON etc.)
     * @throws IOException
     */
    public static String httpGet(String strUrl) throws IOException {
        HttpURLConnection con = null;
        InputStream in = null;
        BufferedReader reader = null;
        String data = null;

        try {
            URL url = new URL(strUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            String encoding = con.getContentEncoding();
            if (null == encoding) {
                encoding = "UTF-8";
            }

            in = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, encoding));

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            data = output.toString();

            int responseCode = con.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Bad response code. response code = " + responseCode + "\n" + data);
            }

        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * HTTP GET
     *
     * @param strUrl
     *            URL address
     * @param headers
     * @return response data (JSON etc.)
     * @throws IOException
     */
    public static String httpGet(String strUrl, Map<String, String> headers) throws IOException {
        HttpURLConnection con = null;
        InputStream in = null;
        BufferedReader reader = null;
        String data = null;

        try {
            URL url = new URL(strUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }

            con.connect();

            String encoding = con.getContentEncoding();
            if (null == encoding) {
                encoding = "UTF-8";
            }

            in = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, encoding));

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            data = output.toString();

            int responseCode = con.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Bad response code. response code = " + responseCode + "\n" + data);
            }

        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * HTTP POST
     *
     * @param strUrl
     *            URL address
     * @param formParam
     *            parameter
     * @return response data (JSON etc.)
     * @throws IOException
     */
    public static String httpPost(String strUrl, String formParam) throws IOException {
        HttpURLConnection con = null;
        InputStream in = null;
        BufferedReader reader = null;
        String data = null;

        try {
            URL url = new URL(strUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(formParam);
            out.close();
            con.connect();

            String encoding = con.getContentEncoding();
            if (null == encoding) {
                encoding = "UTF-8";
            }

            in = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, encoding));

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            data = output.toString();

            int responseCode = con.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Bad response code. response code = " + responseCode + "\n" + data);
            }

        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * HTTP POST
     *
     * @param strUrl
     *            URL address
     * @param headers
     * @param parameters
     * @return response data (JSON etc.)
     * @throws IOException
     */
    public static String httpPost(String strUrl, Map<String, String> headers, Map<String, String> parameters)
            throws IOException {
        HttpURLConnection con = null;
        InputStream in = null;
        BufferedReader reader = null;
        String data = null;

        try {
            URL url = new URL(strUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }

            StringBuffer param = new StringBuffer();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                param.append(entry.getKey());
                param.append("=");
                param.append(entry.getValue());
                param.append("&");
            }
            param.deleteCharAt(param.length() - 1);

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(param.toString());
            out.close();
            con.connect();

            String encoding = con.getContentEncoding();
            if (null == encoding) {
                encoding = "UTF-8";
            }

            in = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, encoding));

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            data = output.toString();

            int responseCode = con.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Bad response code. response code = " + responseCode + "\n" + data);
            }

        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * Cookie
     *
     * @param request
     * @param name
     * @return value
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return (request.getCookies() != null) ? Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equalsIgnoreCase(name)).map(c -> c.getValue()).findFirst().orElse(null) : null;
    }

    /**
     * Cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        setCookie(request, response, name, value, null, null);
    }

    /**
     * Cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param path
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            String path) {
        setCookie(request, response, name, value, path, null);
    }

    /**
     * Cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            Integer maxAge) {
        setCookie(request, response, name, value, null, maxAge);
    }

    /**
     * Cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param path
     * @param maxAge
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            String path, Integer maxAge) {
        setCookie(request, response, name, value, path, maxAge, null, null, false);
    }

    /**
     * Cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param path
     * @param maxAge
     * @param domain
     * @param comment
     * @param httpOnly
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            String path, Integer maxAge, String domain, String comment, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath((path != null) ? path : "/");
        cookie.setMaxAge((maxAge != null) ? maxAge : 0);

        if (domain != null) {
            cookie.setDomain(domain);
        }

        if (comment != null) {
            cookie.setDomain(comment);
        }

        if ("https".equals(request.getScheme())) {
            cookie.setSecure(true);
        }

        response.addCookie(cookie);
    }

    /**
     * multi-language resource
     *
     * @param request
     * @return resource
     */
    public static ResourceBundle getResource(HttpServletRequest request) {
        ResourceBundle bundle;
        String locale = request.getServerName().split("\\.")[0];
        switch (locale) {
            case "en":
            case "ja":
                bundle = ResourceBundle.getBundle("top", new Locale(locale));
                break;
            default:
                Locale reqLocale = request.getLocale();
                bundle = ResourceBundle.getBundle("top", (reqLocale != null) ? reqLocale : Locale.ENGLISH);
        }
        return bundle;
    }
}
