
# Note: Archived as Google changes Bard to Gemini



# Java Library for Google Bard to Ask Questions and Receive Answers

`Google Bard` is Google's experimental, conversational, AI chat service. It is meant to function similarly to ChatGPT, with the biggest difference being that Google's service will pull its information from the web.

I want to use `Google Bard` automatically in an easier way, so I built a [Java library](https://github.com/LarryDpk/Google-Bard) for it. The library supports continuous conversation with the same client.


## How to use the library
### Import the library into your project

For maven project:
```xml
<dependencies>
    <dependency>
        <groupId>com.pkslow</groupId>
        <artifactId>google-bard</artifactId>
        <version>0.3.6</version>
    </dependency>
</dependencies>
```

- 0.2.0+: Support images and it's not compatible with previous versions;
- 0.3.4+: Support Chinese;
- 0.3.5+: need two token: `__Secure-1PSID` and `__Secure-1PSIDTS`
- 0.3.6: Support multiple lines by [Tolunay](https://github.com/TolunayM)

### Java Code
It's easy to make the call:
```java
AIClient client = new GoogleBardClient(token);
Answer answer = client.ask("can you show me a picture of clock?");
```


### Get the token from browser
We need to get the token from browser for authentication. It's cookie named `__Secure-1PSID` and `__Secure-1PSIDTS`, and we need to copy the value.
Combine the two token as `$__Secure-1PSID;$__Secure-1PSIDTS`

Example:
```
ZAiXYL4nedulA03ly0Ea2IdDTk2Emg4YJfuqs3YCcdxxxxxxxxx3XyDzlRqexw.;sidts-CjIBSAxbGXMW7bfOuuE0LOf-DDy20302Dh-npVoG**********86bhkxFwCw4QuaDyz1BAA
```

![](https://pkslow.oss-cn-shenzhen.aliyuncs.com/images/2023/03/google-bard-python-chatbot.sessionid.png)

## SNlM0e is null
If you hit SNlM0e is null issues, you may need to clear the cookie and login again:

- Clear cookies of bard.google.com and again
- login into the account
- F12 > Applications > Cookies > bard > `__Secure-1PSID` and `__Secure-1PSIDTS`
- Copy cookie and paste into your code.
- Re run and you are good to go.

## The Example
[example code to use](https://github.com/LarryDpk/Google-Bard/blob/main/src/main/java/com/pkslow/ai/GoogleBardExample.java)


`Google Bard` is now under development so it may not be available for your country, so you may set the proxy before you run the application:
```java
NetworkUtils.setUpProxy("localhost", "7890");
```


It's `Markdown` format for the answer.
---
```markdown
Sure thing. I found you a few photos of different types of clocks:

* **Analog Clock:** This is the most common type of clock, and it has a face with hands that point to the hour, minute, and second.

[![Image of Analog clock](https://cdn.shopify.com/s/files/1/0556/8066/3742/products/4550344275733_org_1200x1200.jpg?v=1678206891)](https://www.muji.us/products/analog-clock-l-laca0a)
* **Digital Clock:** This type of clock displays the time in numbers, and it can be either battery-powered or plugged into an outlet.

[![Image of Digital clock](https://m.media-amazon.com/images/I/61MuSYQ7yhL._AC_UF894,1000_QL80_.jpg)](https://www.amazon.in/YORTOT-Oversize-Control-Brightness-Temperature/dp/B08R8FW63J)
* **Alarm Clock:** This type of clock is designed to wake you up at a certain time, and it can have a variety of features, such as snooze, a light, and a radio.

[![Image of Alarm clock](https://m.media-amazon.com/images/I/71ggBUmny9L.jpg)](https://www.amazon.com/Sharp-Twin-Bell-Alarm-Clock/dp/B08TB22P29)
* **Sundial:** This type of clock uses the sun to tell time, and it is a popular choice for people who want to live a more sustainable lifestyle.

[![Image of Sundial](https://www.thehoarde.com/resources/images/blog-pictures/Sundial-1-(Deposit-Photos)-21-7-22-crop-v2.jpg)](https://www.thehoarde.com/blog/a-beginners-guide-to-the-garden-sundial)
* **Cuckoo Clock:** This type of clock is a traditional European clock that has a cuckoo bird that pops out to announce the time.

[![Image of Cuckoo clock](https://m.media-amazon.com/images/I/81OBtQVTkuL._AC_UF894,1000_QL80_.jpg)](https://www.amazon.com/Trenkle-Quartz-Cuckoo-Forest-Chopper/dp/B00VZQ5ZTY)

I hope this helps!
```

## Resources
- [Amazing Bard Prompts](https://github.com/dsdanielpark/amazing-bard-prompts)
- [Spring Boot Integration with Google Bard](https://www.pkslow.com/archives/spring-boot-google-bard)
- [Python Lib: acheong08/Bard](https://github.com/acheong08/Bard)