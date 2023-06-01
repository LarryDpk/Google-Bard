
# Java Library for Google Bard to Ask Questions and Receive Answers

`Google Bard` is Google's experimental, conversational, AI chat service. It is meant to function similarly to ChatGPT, with the biggest difference being that Google's service will pull its information from the web.

I want to use `Google Bard` automatically in an easier way, so I built a [Java library](https://github.com/LarryDpk/Google-Bard) for it. Tthe library supports continuous conversation with the same client.


## How to use the library
### Import the library into your project

For maven project:
```xml
<dependencies>
    <dependency>
        <groupId>com.pkslow</groupId>
        <artifactId>google-bard</artifactId>
        <version>0.2.0</version>
    </dependency>
</dependencies>
```

- 0.2.0: Support images and it's not compatible with previous versions;

### Java Code
It's easy to make the call:
```java
AIClient client = new GoogleBardClient(token);
Answer answer = client.ask("can you show me a picture of clock?");
```


### Get the token from browser
We need to get the token from browser for authentication. It a cookie named `__Secure-1PSID`, and we need to copy the value.
![](https://pkslow.oss-cn-shenzhen.aliyuncs.com/images/2023/03/google-bard-python-chatbot.sessionid.png)


## The Example
[example code to use](https://github.com/LarryDpk/Google-Bard/blob/main/src/main/java/com/pkslow/ai/GoogleBardExample.java)


`Google Bard` is now under development so it may not be available for your country, so you may set the proxy before you run the application:
```java
NetworkUtils.setUpProxy("localhost", "7890");
```


It's `Markdown` format for the answer.
---
Sure, here is a picture of a clock.
[Image of Clock]

Do you want to see a picture of a specific type of clock? For example, a digital clock, an analog clock, or a grandfather clock?

[![](https://play-lh.googleusercontent.com/OLkkt0y609LAuCyGnp5pPxEvZkbQ92U5BJXoR-VSexBrCFxGhxXF-R2pv8byLi2Frg)](https://play.google.com/store/apps/details?id=com.egert.clock&hl=zh_TW)

## Resources
- [Amazing Bard Prompts](https://github.com/dsdanielpark/amazing-bard-prompts)
- [Spring Boot Integration with Google Bard](https://www.pkslow.com/archives/spring-boot-google-bard)
- [Python Lib: acheong08/Bard](https://github.com/acheong08/Bard)