
# Java Library for Google Bard to Ask Questions and Receive Answers

`Google Bard` is Google's experimental, conversational, AI chat service. It is meant to function similarly to ChatGPT, with the biggest difference being that Google's service will pull its information from the web.

I want to use `Google Bard` automatically in an easier way, so I built a Java library for it. The GitHub Link is: https://github.com/LarryDpk/Google-Bard, the library supports continuous conversation with the same client.


## How to use the library
### Import the library into your project

For maven project:
```xml
<dependencies>
    <dependency>
        <groupId>com.pkslow</groupId>
        <artifactId>google-bard</artifactId>
        <version>0.0.5</version>
    </dependency>
</dependencies>
```

### Get the token from browser
We need to get the token from browser for authentication. It a cookie named `__Secure-1PSID`, and we need to copy the value.
![](https://pkslow.oss-cn-shenzhen.aliyuncs.com/images/2023/03/google-bard-python-chatbot.sessionid.png)

### Java Code
It's easy to make the call:
```java
AIClient client = new GoogleBardClient(token);
Answer answer = client.ask("how to be a good father?");
```



## The Example
The example Java code below:
```java
public class GoogleBardExample {
    public static void main(String[] args) {
        NetworkUtils.setUpProxy("localhost", "7890");
        String token = args[0];
        AIClient client = new GoogleBardClient(token, Duration.ofMinutes(10));

        Answer answer = client.ask("What is the population of London?");
        printChosenAnswer(answer);

        answer = client.ask("How about Beijing?");
        printChosenAnswer(answer);

        answer = client.ask("How about Hong Kong?");
        printChosenAnswer(answer);
    }
    
    private static void printChosenAnswer(Answer answer) {
        StringBuilder sb = new StringBuilder();
        if (answer.status() == AnswerStatus.OK) {
            sb.append("\n### Chosen Answer\n");
            sb.append(answer.chosenAnswer());
            log.info("Output: \n {}", sb);
        }
    }
}
```


Now we have two ways to create a GoogleBardClient object, we can set the timeout for the http call:
```java
AIClient client = new GoogleBardClient(token);
// or
AIClient client = new GoogleBardClient(token, Duration.ofMinutes(10));
```



If `Google Bard` can answer the question, it will return 3 answers.
The chosen/recommended answer is the first one.

```java
if (answer.status() == AnswerStatus.OK) {
    System.out.println("### Chosen Answer");
    System.out.println(answer.chosenAnswer());
    for (int i = 0; i < answer.draftAnswers().size(); i++) {
        System.out.println("### Draft Answer " + i);
        System.out.println(answer.draftAnswers().get(i));
    }
}
```

`Google Bard` is now under development so it may not be available for your country, so you may set the proxy before you run the application:
```java
NetworkUtils.setUpProxy("localhost", "7890");
```


It's `Markdown` format for the answer.

## Integration
For Integration with Spring Boot, please check below article:

[Spring Boot Integration with Google Bard](https://www.pkslow.com/archives/spring-boot-google-bard)