
# Java Library for Google Bard to Ask Questions and Receive Answers

`Google Bard` is Google's experimental, conversational, AI chat service. It is meant to function similarly to ChatGPT, with the biggest difference being that Google's service will pull its information from the web.

I want to use `Google Bard` automatically in a easier way, so I built a Java library for it. The GitHub Link is: https://github.com/LarryDpk/Google-Bard


## How to use the library
(1) Import the library into your project

For maven project:
```xml
<dependencies>
    <dependency>
        <groupId>com.pkslow</groupId>
        <artifactId>google-bard</artifactId>
        <version>0.0.3</version>
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

`Google Bard` is now under development, it's just available in US/UK, so you may set the proxy before you run the application:
```java
NetworkUtils.setUpProxy("localhost", "7890");
```


## The answers Example
It `Markdown` format for the answer, you can see the answers I got:

### Chosen Answer
Here are some tips on how to be a good father:

* **Spend time with your child.** Quality time is essential for building a strong relationship with your child. Make time for activities that you both enjoy, such as playing games, going to the park, or reading stories.
* **Be a good role model.** Children learn by watching the adults in their lives. Set a good example by being honest, kind, and respectful.
* **Be supportive.** Let your child know that you are there for them, no matter what. Offer them your support and encouragement, especially when they are going through tough times.
* **Be a good communicator.** Talk to your child about their day, their feelings, and their hopes and dreams. Let them know that they can come to you with anything.
* **Be patient.** Parenting is a journey, not a destination. There will be times when you feel frustrated or overwhelmed. Remember to be patient with yourself and your child.
* **Be loving.** The most important thing a father can do is to love his child unconditionally. Let your child know that you love them every day.

Being a good father is a lifelong journey. There is no one right way to do it. Just remember to be there for your child, be a good role model, and show them love.
### Draft Answer 0
Here are some tips on how to be a good father:

* **Spend time with your child.** Quality time is essential for building a strong relationship with your child. Make time for activities that you both enjoy, such as playing games, going to the park, or reading stories.
* **Be a good role model.** Children learn by watching the adults in their lives. Set a good example by being honest, kind, and respectful.
* **Be supportive.** Let your child know that you are there for them, no matter what. Offer them your support and encouragement, especially when they are going through tough times.
* **Be a good communicator.** Talk to your child about their day, their feelings, and their hopes and dreams. Let them know that they can come to you with anything.
* **Be patient.** Parenting is a journey, not a destination. There will be times when you feel frustrated or overwhelmed. Remember to be patient with yourself and your child.
* **Be loving.** The most important thing a father can do is to love his child unconditionally. Let your child know that you love them every day.

Being a good father is a lifelong journey. There is no one right way to do it. Just remember to be there for your child, be a good role model, and show them love.
### Draft Answer 1
Here are some tips on how to be a good father:

* **Spend time with your children.** Make time for them, even if it's just for a few minutes each day. Talk to them, listen to them, and play with them.
* **Discipline with love and positive parenting.** Set rules and boundaries, but be fair and consistent. Use positive reinforcement, such as praise and rewards, to encourage good behavior.
* **Be your child's role model.** Show them what it means to be a good person by being honest, trustworthy, and kind.
* **Earn the right to be heard.** Be a good listener and take your children's concerns seriously. When you give them advice, make sure it's based on your own experience and wisdom.
* **Be your child's teacher.** Teach them about the world around them and help them to grow into responsible, independent adults.
* **Be there for your children, no matter what.** Love them unconditionally and support them through thick and thin.

Being a good father is a lifelong journey. There will be times when you make mistakes, but that's okay. The important thing is to learn from your mistakes and keep trying to be the best father you can be.
### Draft Answer 2
Here are some tips on how to be a good father:

* **Spend time with your child.** This is one of the most important things you can do as a father. Make time for your child every day, even if it's just for a few minutes. Play with them, read to them, or just talk to them.
* **Discipline with love and positive parenting.** Discipline is important, but it should be done in a loving and positive way. Avoid yelling or hitting your child. Instead, try to explain why they are being disciplined and what they can do to avoid being disciplined in the future.
* **Be your child's role model.** Children learn by watching the adults in their lives. Show your child what it means to be a good person by being someone they can look up to.
* **Earn the right to be heard.** Children are more likely to listen to their parents if they respect them. Earn your child's respect by being a good role model and by being fair and consistent with your discipline.
* **Be your child's teacher.** Teach your child about right and wrong and encourage them to do their best. Use everyday examples to help them learn the basic lessons of life.
* **Be there for your child.** Your child needs to know that you are there for them, no matter what. Be supportive and loving, and let them know that you will always be there for them.

Being a good father is one of the most important things you can do in life. By following these tips, you can help your child grow into a happy, healthy, and successful adult.

