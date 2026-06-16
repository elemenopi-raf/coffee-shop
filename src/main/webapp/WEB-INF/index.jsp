<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
  String flashMsg = (String) session.getAttribute("formMsg");
  String flashType = (String) session.getAttribute("formMsgType");
  if (flashMsg != null) {
    request.setAttribute("formMsg", flashMsg);
    request.setAttribute("formMsgType", flashType);
    session.removeAttribute("formMsg");
    session.removeAttribute("formMsgType");
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Brew & Bean</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

  <header>
    <nav>
      <a href="#home">Home</a>
      <a href="#menu">Menu</a>
      <a href="#about">About</a>
      <a href="#testimonials">Testimonials</a>
      <a href="#contact">Contact</a>
    </nav>
    <div class="hero" id="home">
      <h1>Brew & Bean</h1>
      <p>Where every cup tells a story. Artisan coffee crafted with passion.</p>
      <a href="#menu" class="btn">Explore Our Menu</a>
    </div>
  </header>

  <section id="menu">
    <h2>Our Menu</h2>
    <div class="menu-grid">
      <c:forEach var="item" items="${menuItems}">
        <div class="menu-item">
          <div class="icon">${item.icon}</div>
          <h3>${item.name}</h3>
          <p class="price">${item.price}</p>
        </div>
      </c:forEach>
    </div>
  </section>

  <section id="about">
    <h2>About Us</h2>
    <div class="about-content">
      <img src="https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=500" alt="Coffee shop interior">
      <div class="about-text">
        <p>Founded in 2015, Brew & Bean started as a small dream between two friends who believed that great coffee brings people together. We source our beans directly from sustainable farms in Ethiopia, Colombia, and Guatemala.</p>
        <p>Every drink is handcrafted by our skilled baristas who treat each cup as a work of art. Whether you need a quiet corner to read or a vibrant space to catch up with friends, our doors are always open.</p>
      </div>
    </div>
  </section>

  <section id="testimonials">
    <h2>What Our Customers Say</h2>
    <div class="testimonial-grid">
      <c:forEach var="t" items="${testimonials}">
        <div class="testimonial">
          <p>${t.text}</p>
          <p class="author">- ${t.author}</p>
        </div>
      </c:forEach>
    </div>
  </section>

  <section id="contact">
    <h2>Get In Touch</h2>
    <form class="contact-form" action="${pageContext.request.contextPath}/contact" method="post">
      <input type="text" id="name" name="name" placeholder="Your Name" required>
      <input type="email" id="email" name="email" placeholder="Your Email" required>
      <textarea id="message" name="message" placeholder="Your Message" required></textarea>
      <button type="submit" class="btn">Send Message</button>
      <c:if test="${not empty formMsg}">
        <p id="form-message" style="color: ${formMsgType == 'success' ? '#27ae60' : '#e74c3c'}">${formMsg}</p>
      </c:if>
    </form>
  </section>

  <footer>
    <p>&copy; 2026 <span>Brew & Bean</span>. All rights reserved.</p>
  </footer>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
