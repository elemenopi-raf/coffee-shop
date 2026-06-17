<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin — Brew & Bean</title>
  <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>☕</text></svg>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    body { padding: 2rem; background: #f5ede4; }
    h1 { margin-bottom: 2rem; color: #2c1810; }
    table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.06); }
    th, td { padding: 0.8rem 1rem; text-align: left; border-bottom: 1px solid #e8ddd0; }
    th { background: #2c1810; color: #fff; font-weight: 600; }
    tr:hover { background: #faf6f1; }
    .msg-text { max-width: 300px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
    .back-link { display: inline-block; margin-bottom: 1.5rem; color: #d4a574; text-decoration: none; font-weight: 600; }
    .back-link:hover { text-decoration: underline; }
    .empty { text-align: center; padding: 3rem; color: #5c3a28; }
    .error { color: #e74c3c; margin-bottom: 1rem; }
  </style>
</head>
<body>
  <a href="${pageContext.request.contextPath}/" class="back-link">&larr; Back to site</a>
  <h1>Contact Messages</h1>

  <c:if test="${not empty error}">
    <p class="error">${error}</p>
  </c:if>

  <c:choose>
    <c:when test="${empty messages}">
      <p class="empty">No messages yet.</p>
    </c:when>
    <c:otherwise>
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>Email</th>
            <th>Message</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="msg" items="${messages}">
            <tr>
              <td>${msg.id}</td>
              <td>${msg.name}</td>
              <td>${msg.email}</td>
              <td class="msg-text" title="${msg.message}">${msg.message}</td>
              <td>${msg.createdAt}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>
</body>
</html>