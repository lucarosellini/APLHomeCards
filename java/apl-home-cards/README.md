# Alexa APL Cards

This project delivers a response builder wrapper to transparently send home card like responses to multimodal devices using APL.

To use it in your project:
- Enable APL in the voice interaction model Interfaces section
- Import the following dependency:

```
<dependency>
    <groupId>com.github.lucarosellini.alexa</groupId>
    <artifactId>apl-home-cards</artifactId>
    <version>1.0.0</version>
</dependency>
```
- In you skill builder, add a request interceptor like this: ``.addRequestInterceptor(new APLHomeCardInterceptor())```
- Just use home cards as usual! (```.withSimpleCard(...)```, ```.withStandardCard(...)```)
