# 🚀 SyntaxGo: Odaklanma Odaklı Çevrimdışı Kod Okuyucu

Modern mobil cihazlarda düz metin veya kod dosyalarını (.java, .cs, .py vb.) okumak genellikle hantal ve reklamlara boğulmuş uygulamalarla mümkündür. SyntaxGo, yazılımcıların ve bilgisayar bilimleri öğrencilerinin kod okuma ve sınavlara hazırlanma sürecini **dikkat dağıtıcı unsurlardan arındırarak** çözen minimalist bir Android uygulamasıdır.

## 🎯 Neden Geliştirildi?
Bu proje, bilgisayardan telefona aktarılan kod dosyalarını mobil ortamda, internet bağlantısına ihtiyaç duymadan ve profesyonel bir IDE (örneğin VS Studio) renklendirmesiyle okuyabilme ihtiyacından doğmuştur.

## ⚙️ Temel Özellikler
* **Modern Dosya Erişimi:** Android 10+ ile tam uyumlu Storage Access Framework (SAF) kullanarak güvenli ve cihazı yormayan dosya seçimi.
* **Tamamen Çevrimdışı (Offline) Çalışma:** Metroda veya internetin olmadığı yerlerde bile çalışabilmesi için `highlight.js` kütüphanesi yerel (assets) olarak entegre edilmiştir.
* **Gelişmiş Sözdizimi Vurgulama (Syntax Highlighting):** WebView ve HTML/CSS manipülasyonu ile 190'dan fazla programlama dilini otomatik tanıma ve renklendirme.
* **Minimalist Arayüz:** Sadece koda odaklanmayı sağlayan, gereksiz butonlardan arındırılmış UI tasarımı.

## 🛠️ Kullanılan Teknolojiler
* **Dil:** Java (Android Native)
* **API & Kütüphaneler:** Android SDK, SAF (ContentResolver), WebKit (WebView)
* **İşaretleme & Stil:** HTML, CSS, [Highlight.js](https://highlightjs.org/)

## 📸 Ekran Görüntüleri
*(Buraya bana attığın o son ekran görüntüsünü koymalısın)*
![SyntaxGo Ekran Görüntüsü](ekran_goruntusu_linki_buraya_gelecek)

## 🚀 Kurulum ve Çalıştırma
1. Bu repoyu bilgisayarınıza klonlayın.
2. Android Studio ile projeyi açın.
3. Gradle senkronizasyonunu tamamladıktan sonra fiziksel cihazınızda veya emülatörde çalıştırın.