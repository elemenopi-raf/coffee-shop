<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%
  String flashMsg = (String) session.getAttribute("formMsg");
  String flashType = (String) session.getAttribute("formMsgType");
  if (flashMsg != null) {
    request.setAttribute("formMsg", flashMsg);
    request.setAttribute("formMsgType", flashType);
    session.removeAttribute("formMsg");
    session.removeAttribute("formMsgType");
  }
  String nlMsg = (String) session.getAttribute("newsletterMsg");
  if (nlMsg != null) {
    request.setAttribute("newsletterMsg", nlMsg);
    session.removeAttribute("newsletterMsg");
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Brew & Bean Coffee Shop</title>
  <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>☕</text></svg>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

  <header id="home">
    <nav id="navbar" class="navbar">
      <a href="#home" class="logo">Brew & Bean</a>
      <button class="hamburger" id="hamburger" aria-label="Toggle navigation">
        <span></span><span></span><span></span>
      </button>
      <div class="nav-links" id="nav-links">
        <a href="#menu">Menu</a>
        <a href="#about">About</a>
        <a href="#visit">Visit</a>
        <a href="#testimonials">Testimonials</a>
        <a href="#contact">Contact</a>
      </div>
    </nav>
    <div class="hero">
      <h1 class="hero-title">Brew & Bean</h1>
      <p class="hero-subtitle">Where every cup tells a story. Artisan coffee crafted with passion.</p>
      <a href="#menu" class="btn">Explore Our Menu</a>
    </div>
  </header>

  <section id="menu">
    <h2>Our Menu</h2>

    <h3 class="menu-category-title">☕ Hot Drinks</h3>
    <div class="menu-grid">
      <c:forEach var="item" items="${menuItems}">
        <c:if test="${item.category eq 'HOT'}">
          <div class="menu-item">
            <img class="menu-img" src="${item.imageUrl}" alt="${item.name}" loading="lazy">
            <h4>${item.name}</h4>
            <p class="price"><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="$"/></p>
          </div>
        </c:if>
      </c:forEach>
    </div>

    <h3 class="menu-category-title">🧊 Cold Drinks</h3>
    <div class="menu-grid">
      <c:forEach var="item" items="${menuItems}">
        <c:if test="${item.category eq 'COLD'}">
          <div class="menu-item">
            <img class="menu-img" src="${item.imageUrl}" alt="${item.name}" loading="lazy">
            <h4>${item.name}</h4>
            <p class="price"><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="$"/></p>
          </div>
        </c:if>
      </c:forEach>
    </div>

    <h3 class="menu-category-title">🥐 Pastry</h3>
    <div class="menu-grid">
      <c:forEach var="item" items="${menuItems}">
        <c:if test="${item.category eq 'PASTRY'}">
          <div class="menu-item">
            <img class="menu-img" src="${item.imageUrl}" alt="${item.name}" loading="lazy">
            <h4>${item.name}</h4>
            <p class="price"><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="$"/></p>
          </div>
        </c:if>
      </c:forEach>
    </div>
  </section>

  <section id="about" class="fade-in">
    <h2>About Us</h2>
    <div class="about-content">
      <img src="https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=500" alt="Warm interior of Brew & Bean coffee shop">
      <div class="about-text">
        <p>Founded in 2015, Brew & Bean started as a small dream between two friends who believed that great coffee brings people together. We source our beans directly from sustainable farms in Ethiopia, Colombia, and Guatemala.</p>
        <p>Every drink is handcrafted by our skilled baristas who treat each cup as a work of art. Whether you need a quiet corner to read or a vibrant space to catch up with friends, our doors are always open.</p>
      </div>
    </div>
  </section>

  <section id="visit" class="fade-in">
    <h2>Visit Us</h2>
    <div class="visit-grid">
      <div class="visit-info">
        <div class="visit-info-item">
          <span class="icon">📍</span>
          <div>
            <h4>Address</h4>
            <p>123 Coffee Lane, Bean District<br>Seattle, WA 98101</p>
          </div>
        </div>
        <div class="visit-info-item">
          <span class="icon">🕐</span>
          <div>
            <h4>Hours</h4>
            <p>Mon–Fri: 6:00 AM – 7:00 PM<br>Sat–Sun: 7:00 AM – 6:00 PM</p>
          </div>
        </div>
        <div class="visit-info-item">
          <span class="icon">📞</span>
          <div>
            <h4>Phone</h4>
            <p>(555) 123-4567</p>
          </div>
        </div>
        <div class="visit-info-item">
          <span class="icon">📶</span>
          <div>
            <h4>Free Wi-Fi</h4>
            <p>Network: BrewBean | Password: freshbrew2026</p>
          </div>
        </div>
      </div>
      <div class="visit-map">
        <iframe
          src="https://maps.google.com/maps?q=coffee+shop+seattle&output=embed"
          width="100%"
          height="300"
          style="border:0"
          allowfullscreen=""
          loading="lazy"
          referrerpolicy="no-referrer-when-downgrade"
          title="Brew & Bean location on Google Maps">
        </iframe>
      </div>
    </div>
  </section>

  <section id="testimonials" class="fade-in">
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

  <section id="contact" class="fade-in">
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
    <div class="footer-content">
      <div class="footer-col">
        <h4>Brew & Bean</h4>
        <p>Artisan coffee crafted with passion since 2015. Every cup tells a story.</p>
      </div>
      <div class="footer-col">
        <h4>Stay in Touch</h4>
        <form class="newsletter-form" action="${pageContext.request.contextPath}/newsletter" method="post">
          <input type="email" name="email" placeholder="Your email" required>
          <button type="submit" class="btn btn-sm">Subscribe</button>
        </form>
        <c:if test="${not empty newsletterMsg}">
          <p id="newsletter-message" style="color: #27ae60; font-size: 0.85rem; margin-top: 0.5rem;">${newsletterMsg}</p>
        </c:if>
      </div>
      <div class="footer-col">
        <h4>Follow Us</h4>
        <div class="social-links">
          <a href="#" aria-label="Instagram">📷</a>
          <a href="#" aria-label="Facebook">👍</a>
          <a href="#" aria-label="Twitter / X">🐦</a>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; 2026 <span>Brew & Bean</span>. All rights reserved. Crafted with ☕ and care.</p>
    </div>
  </footer>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
