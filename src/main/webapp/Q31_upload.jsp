<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, jakarta.servlet.http.Part, java.nio.file.Paths, java.text.SimpleDateFormat, java.util.Date" %>
<!DOCTYPE html>
<html>
<head><title>File Upload</title></head>
<body>
    <h2>Q31 - File Upload to Server</h2>
    <form action="Q31_upload.jsp" method="post" enctype="multipart/form-data">
        <label>Select file to upload:</label><br><br>
        <input type="file" name="uploadFile" required><br><br>
        <input type="submit" value="Upload">
    </form>
    <hr>
<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        Part filePart = request.getPart("uploadFile");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = application.getRealPath("/") + "WEB-INF" + File.separator + "uploads";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String filePath = uploadDir + File.separator + fileName;
            File existingFile = new File(filePath);
            if (existingFile.exists()) {
                String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
                String ext = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.')) : "";
                fileName = baseName + "_" + System.currentTimeMillis() + ext;
                filePath = uploadDir + File.separator + fileName;
            }
            try (InputStream is = filePart.getInputStream();
                 OutputStream os = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                long totalBytes = 0;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }
                out.println("<h3 style='color:green;'>File uploaded successfully!</h3>");
                out.println("<table border='1' cellpadding='8' style='border-collapse:collapse;'>");
                out.println("<tr><th>File Name</th><td>" + fileName + "</td></tr>");
                out.println("<tr><th>File Size</th><td>" + totalBytes + " bytes</td></tr>");
                out.println("<tr><th>Saved At</th><td>" + uploadDir + "</td></tr>");
                out.println("<tr><th>Upload Time</th><td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</td></tr>");
                out.println("</table>");
            }
        } else {
            out.println("<h3 style='color:red;'>No file selected or upload failed.</h3>");
        }
    }
%>
</body>
</html>
