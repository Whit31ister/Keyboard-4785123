# Fossify Keyboard

<img alt="Logo" src="graphics/icon.webp" width="120" />

<a href="https://play.google.com/store/apps/details?id=org.fossify.keyboard"><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' height=80/></a> <a href="https://f-droid.org/packages/org.fossify.keyboard/"><img src="https://fdroid.gitlab.io/artwork/badge/get-it-on-en.svg" alt="Get it on F-Droid" height=80/></a> <a href="https://apt.izzysoft.de/fdroid/index/apk/org.fossify.keyboard"><img src="https://gitlab.com/IzzyOnDroid/repo/-/raw/master/assets/IzzyOnDroid.png" alt="Get it on IzzyOnDroid" height=80/></a>

Introducing Fossify Keyboard – your go-to solution for effortless and efficient typing. Experience a seamless typing experience designed to cater to all your needs, whether chatting with friends or inserting texts, numbers, or symbols.

**📶 OFFLINE FUNCTIONALITY:**    
Fossify Keyboard's core typing features operate offline. Optional GIF search requires internet access only when the GIF panel is used and a GIF API key is configured; typing, emojis, and saved clips remain available without a connection.

**🌐 MULTIPLE LANGUAGES AND LAYOUTS:**    
Choose from a wide variety of languages and keyboard layouts. Fossify Keyboard supports multiple languages, making it easy for you to switch and type in your preferred language effortlessly.

**📋 HANDY CLIPBOARD:**    
Create clips and pin frequently used ones for easy access. This feature allows you to insert your most-used texts quickly, saving you time and effort.

**📳 CUSTOMIZABLE SETTINGS:**    
Tailor your typing experience by toggling vibrations, popups on key presses, and selecting your preferred language from the list of supported ones. Personalize your keyboard settings to suit your preferences.

**🌙 MATERIAL DESIGN AND DARK THEME:**    
Enjoy a sleek, modern design with a default dark theme. Fossify Keyboard offers a visually appealing and comfortable user experience, making typing a pleasure.

**🔒 PRIVACY AND SECURITY:**    
Your privacy is our top priority. Fossify Keyboard does not collect or share any user information with third parties. Experience peace of mind knowing your typing activity remains private and secure.

**🎞️ GIF SEARCH:**
The optional GIF panel currently searches and previews GIFs through Tenor API v2. It requires an internet connection when the panel is opened and a Tenor API key to be configured at build time. GIFs are downloaded only after selection and are inserted directly into apps that support `image/gif`; otherwise, the GIF URL is inserted as a fallback. Builds without a key keep the GIF panel unavailable.

GIF provider access is configured with the `GIF_API_KEY` Gradle property or environment variable:

```bash
GIF_API_KEY=your_provider_key ./gradlew assembleDebug
```

The key is compiled into the application for this client-side implementation, so it must be treated as public and restricted according to the provider's limits. For a production release, use a small backend proxy to keep provider credentials off-device and to provide rate limiting, caching, moderation, and the flexibility to change providers. Verify Tenor's current API availability and review the selected provider's availability, attribution, content, and commercial-use terms before distributing a release build.

The GIF panel is an optional online feature; normal typing, emojis, and saved clips continue to work offline.

**🎨 CUSTOMIZABLE COLORS:**    
Personalize your keyboard with customizable colors. Fossify Keyboard allows you to choose and adjust colors to match your style and preferences.

**🌐 OPEN-SOURCE TRANSPARENCY:**    
Fossify Keyboard is fully open-source, providing you with transparency and security. You have access to the source code for audits, ensuring a trustworthy and reliable typing tool.

Experience typing like never before – efficient, personalized, and secure. Download Fossify Keyboard now and elevate your typing experience.

➡️ Explore more Fossify apps: https://www.fossify.org    
➡️ Open-Source Code: https://www.github.com/FossifyOrg    
➡️ Join the community on Reddit: https://www.reddit.com/r/Fossify    
➡️ Connect on Telegram: https://t.me/Fossify    

<div align="center">
<img alt="App image" src="fastlane/metadata/android/en-US/images/phoneScreenshots/1_en-US.png" width="30%">
<img alt="App image" src="fastlane/metadata/android/en-US/images/phoneScreenshots/2_en-US.png" width="30%">
<img alt="App image" src="fastlane/metadata/android/en-US/images/phoneScreenshots/3_en-US.png" width="30%">
</div>
