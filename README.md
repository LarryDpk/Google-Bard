
# Java Library for Google Bard to Ask Questions and Receive Answers

`Google Bard` is Google's experimental, conversational, AI chat service. It is meant to function similarly to ChatGPT, with the biggest difference being that Google's service will pull its information from the web.

I want to use `Google Bard` automatically in an easier way, so I built a Java library for it. The GitHub Link is: https://github.com/LarryDpk/Google-Bard, the library supports continuous conversation with the same client.


## How to use the library
(1) Import the library into your project

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

## Get the token from browser
We need to get the token from browser for authentication. It a cookie named `__Secure-1PSID`, and we need to copy the value.
![](https://pkslow.oss-cn-shenzhen.aliyuncs.com/images/2023/03/google-bard-python-chatbot.sessionid.png)

## Java Code
It's easy to make the call:
```java
AIClient client = new GoogleBardClient(token);
Answer answer = client.ask("how to be a good father?");
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


It's `Markdown` format for the answer, you can see the answers I got:


### What is the population of London?
The population of London is 8,799,800 as of 2021. This makes it the most populous city in the United Kingdom and the ninth most populous city in Europe. London is also a global city, with a significant impact on the world economy, culture, and politics.

The population of London has been growing steadily for centuries. In the 18th century, the city's population was only about 1 million. However, it grew rapidly during the Industrial Revolution, as people flocked to the city in search of work. By the early 20th century, London's population had reached 7 million.

The population of London continued to grow after World War II, but it has slowed in recent decades. This is due to a number of factors, including the rising cost of living and the decline of manufacturing jobs. However, London remains a popular destination for immigrants, and its population is expected to continue to grow in the coming years.

### How about Beijing?
The population of Beijing is 21,766,000 as of 2023. This makes it the most populous city in China and the second most populous city in the world. Beijing is also a global city, with a significant impact on the world economy, culture, and politics.

The population of Beijing has been growing steadily for centuries. In the 18th century, the city's population was only about 1 million. However, it grew rapidly during the Qing dynasty, as people flocked to the city in search of work. By the early 20th century, Beijing's population had reached 5 million.

The population of Beijing continued to grow after the founding of the People's Republic of China in 1949. However, it has slowed in recent decades due to a number of factors, including the rising cost of living and the decline of manufacturing jobs. However, Beijing remains a popular destination for immigrants, and its population is expected to continue to grow in the coming years.

Here are some interesting facts about the population of Beijing:

* The average age of a resident of Beijing is 34.
* The majority of residents of Beijing are Han Chinese, with a small minority of other ethnic groups.
* The most common religion in Beijing is Buddhism, followed by Taoism and Confucianism.
* The official language of Beijing is Mandarin Chinese.
* The most popular tourist attractions in Beijing include the Forbidden City, the Great Wall of China, and Tiananmen Square.

### How about Hong Kong?
As of 2023, the population of Hong Kong is 7,670,674. This makes it the most populous city in Hong Kong Special Administrative Region (HKSAR) of the People's Republic of China and the 104th most populous city in the world. The population density of Hong Kong is 7,140 per Km2 (18,492 people per mi2), making it one of the most densely populated places in the world.

The majority of the population of Hong Kong is Han Chinese, with a small minority of other ethnic groups, including the indigenous people of Hong Kong, the Tanka. The most common language spoken in Hong Kong is Cantonese, followed by Mandarin Chinese. The official languages of Hong Kong are English and Chinese.

The population of Hong Kong has been growing steadily for centuries. In the 18th century, the city's population was only about 100,000. However, it grew rapidly during the British colonial period, as people flocked to the city in search of work. By the early 20th century, Hong Kong's population had reached 1 million.

The population of Hong Kong continued to grow after the handover of Hong Kong to China in 1997. However, it has slowed in recent decades due to a number of factors, including the rising cost of living and the decline of manufacturing jobs. However, Hong Kong remains a popular destination for immigrants, and its population is expected to continue to grow in the coming years.

Here are some interesting facts about the population of Hong Kong:

* The average age of a resident of Hong Kong is 44.8.
* The majority of residents of Hong Kong are Han Chinese, with a small minority of other ethnic groups.
* The most common religion in Hong Kong is Buddhism, followed by Taoism and Confucianism.
* The official languages of Hong Kong are English and Chinese.
* The most popular tourist attractions in Hong Kong include Victoria Harbour, the Peak, and the Temple Street Night Market.