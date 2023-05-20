
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
        <version>0.1.1</version>
    </dependency>
</dependencies>
```

### Java Code
It's easy to make the call:
```java
AIClient client = new GoogleBardClient(token);
Answer answer = client.ask("how to be a good father?");
```


### Get the token from browser
We need to get the token from browser for authentication. It a cookie named `__Secure-1PSID`, and we need to copy the value.
![](https://pkslow.oss-cn-shenzhen.aliyuncs.com/images/2023/03/google-bard-python-chatbot.sessionid.png)


## The Example
[example code to use](https://github.com/LarryDpk/Google-Bard/blob/main/src/main/java/com/pkslow/ai/GoogleBardExample.java)


If `Google Bard` can answer the question, it will return 3 answers.
The chosen/recommended answer is the first one.

```java
Assert.assertEquals(3, answer.draftAnswers().size());
Assert.assertEquals(answer.chosenAnswer(), answer.draftAnswers().get(0));
```

`Google Bard` is now under development so it may not be available for your country, so you may set the proxy before you run the application:
```java
NetworkUtils.setUpProxy("localhost", "7890");
```


It's `Markdown` format for the answer.

## Resources
- [Amazing Bard Prompts](https://github.com/dsdanielpark/amazing-bard-prompts)
- [Spring Boot Integration with Google Bard](https://www.pkslow.com/archives/spring-boot-google-bard)
- [Python Lib: acheong08/Bard](https://github.com/acheong08/Bard)