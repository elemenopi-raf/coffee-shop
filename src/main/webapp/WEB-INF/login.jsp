<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Login — Brew & Bean</title>
  <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>☕</text></svg>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    body { min-height: 100vh; display: flex; align-items: center; justify-content: center; background: #f5ede4; padding: 2rem; }
    .login-box { background: #fff; padding: 3rem; border-radius: 14px; box-shadow: 0 8px 25px rgba(0,0,0,0.08); width: 100%; max-width: 360px; }
    .login-box h1 { text-align: center; font-size: 1.6rem; color: #2c1810; margin-bottom: 2rem; }
    .login-box h1::after { content: ''; display: block; width: 40px; height: 3px; background: #d4a574; margin: 0.75rem auto 0; border-radius: 2px; }
    .login-box form { display: flex; flex-direction: column; gap: 1rem; }
    .login-box input { padding: 0.9rem 1.2rem; border: 2px solid #e8ddd0; border-radius: 10px; font-size: 1rem; font-family: inherit; transition: border-color 0.3s, box-shadow 0.3s; }
    .login-box input:focus { outline: none; border-color: #d4a574; box-shadow: 0 0 0 3px rgba(212,165,116,0.15); }
    .login-box .btn { width: 100%; text-align: center; }
    .error { color: #e74c3c; text-align: center; font-size: 0.9rem; margin: 0; }
  </style>
</head>
<body>
  <div class="login-box">
    <h1>Admin Login</h1>
    <form action="${pageContext.request.contextPath}/admin/login" method="post">
      <input type="text" name="username" placeholder="Username" required>
      <input type="password" name="password" placeholder="Password" required>
      <button type="submit" class="btn">Sign In</button>
      <c:if test="${not empty loginError}">
        <p class="error">${loginError}</p>
      </c:if>
    </form>
  </div>
</body>
</html>
