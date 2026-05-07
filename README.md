# ☕ Coffee Game - Android

Kahve dükkanı sadakat uygulamalarını eğlenceli hale getiren bir mobil oyun. Kahve yap, puan kazan, ödül kazan!

## Hakkında

Coffee Game, Nero, Starbucks gibi kahve zincirlerinin sıkıcı puan biriktirme sistemlerini oyunlaştıran bağımsız bir mobil uygulamadır. Kullanıcılar 2D bir kahve yapım oyunu oynayarak gerçek ödüller kazanır.

## Ekranlar

- **Splash Screen** — Animasyonlu açılış ekranı
- **Login / Register** — Kullanıcı girişi ve kayıt
- **Ana Ekran** — Puan durumu ve oyun geçmişi

## Teknolojiler

- **Kotlin** — Ana programlama dili
- **Jetpack Compose** — Modern Android UI
- **Retrofit + OkHttp** — Backend API iletişimi
- **Navigation Compose** — Ekranlar arası geçiş
- **ViewModel + StateFlow** — State yönetimi
- **Material 3** — Tasarım sistemi

## Kurulum

1. Repoyu klonla:
```bash
git clone https://github.com/Mehmetsdk/coffee-game-android.git
```

2. Android Studio'da aç

3. Backend sunucusunun çalıştığından emin ol → [coffee-game-backend](https://github.com/Mehmetsdk/coffee-game-backend)

4. Uygulamayı çalıştır (emülatör veya fiziksel cihaz)

## Backend

Bu uygulama Ktor ile yazılmış ayrı bir backend sunucusuna bağlanır.
Backend reposu: [coffee-game-backend](https://github.com/Mehmetsdk/coffee-game-backend)

## Yol Haritası

- [ ] 2D kahve yapım oyunu (Korge)
- [ ] Liderlik tablosu
- [ ] Haftalık ödül sistemi
- [ ] Çoklu mağaza desteği (white-label)
- [ ] Push notification
