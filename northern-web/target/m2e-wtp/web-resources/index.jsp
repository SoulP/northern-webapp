<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.io.IOException"%>
<%@page import="cloud.northern.util.Utility"%>
<%@page import="cloud.northern.util.PropertyUtil"%>
<%
    ResourceBundle bundle = Utility.getResource(request);

    String accessToken = Utility.getCookie(request, "access_token");
    String userInfo = "null";
    if (accessToken != null && !accessToken.equals("")) {
        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("Authorization", "Bearer " + accessToken);

        String url = PropertyUtil.get("base.auth.url");
        url += PropertyUtil.get("aws.userinfo.end_point");

        try {
            userInfo = Utility.httpGet(url, headers);
        } catch (IOException e) {
            //response.sendRedirect(PropertyUtil.get("base.url") + "/logout");
        }
    }
%>
<!DOCTYPE html>
<html lang="<%=bundle.getString("lang")%>">
<head prefix="og: http://ogp.me/ns# profile: http://ogp.me/ns/profile#">
<meta charset="UTF-8">
<meta name="Date" content="2019/06/03">
<meta name="distribution" content="Global">
<meta name="description" content="<%=bundle.getString("description")%>">
<meta name="abstract" content="Top">
<meta name="keywords" content="Northern">
<meta name="copyright" content="(C) 2019 Northern">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="msapplication-TileImage"
	content="/img/icon/northern_144x144.png">
<meta name="msapplication-TileColor" content="#00FF90">
<meta property="og:url" content="https://nort.cloud/">
<meta property="og:title" content="Northern">
<meta property="og:type" content="website">
<meta property="og:description"
	content="<%=bundle.getString("description")%>">
<meta property="og:image" content="/img/icon/northern-ogp.png">
<meta property="og:site_name" content="Northern">
<meta property="og:locale" content="<%=bundle.getString("locale")%>">
<link rel="icon" type="image/png" href="/img/icon/northern_16x16.png"
	sizes="16x16">
<link rel="icon" type="image/png" href="/img/icon/northern_32x32.png"
	sizes="32x32">
<link rel="icon" type="image/png" href="/img/icon/northern_64x64.png"
	sizes="64x64">
<link rel="canonical" href="https://nort.cloud/" />
<link rel="alternate" hreflang="en" href="https://en.nort.cloud/">
<link rel="alternate" hreflang="ja" href="https://ja.nort.cloud/">
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
<script type="text/javascript" src="/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/js/popper.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<title>Northern</title>
</head>
<body>
	<h1>Hello!!</h1>
	<a href="/login"><%=bundle.getString("login")%></a>
	<br>
	<a href="/logout"><%=bundle.getString("logout")%></a>
	<br> userInfo
	<br>
	<p><%=userInfo%></p>
</body>
</html>