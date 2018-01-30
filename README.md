# Spring Boot Actuator - `WebMvcMetricsFilter` breaks character encoding

This issue affects Spring Boot `2.0.0.M6` onwards and was fixed in `2.0.0.RC1`. See [gh-11607](https://github.com/spring-projects/spring-boot/issues/11607).

---

This is a minimal project to demonstrate issue which renders character encoding broken due to Actuator's `WebMvcMetricsFilter` ordering and its interaction with HTTP requests POST data.

Actuator's `WebMvcMetricsFilter` has a hard-coded order of `Ordered.HIGHEST_PRECEDENCE`, which in practice puts it ahead of `CharacterEncodingFilter`. Since `WebMvcMetricsFilter` performs the mapping introspection, under some circumstances it will touch the POST data hence breaking the `CharacterEncodingFilter`.

To demonstrate the issue, start this sample app using `./gradlew bootRun`, and issue the following HTTP POST request (samples use HTTPie):

```shell
$ http -f POST :8080/pv/post name='Vedran Pavić' Content-Type:'application/x-www-form-urlencoded'
HTTP/1.1 200 
Content-Length: 21
Content-Type: text/plain;charset=UTF-8
Date: Thu, 11 Jan 2018 17:58:44 GMT

Hello Vedran PaviÄ�
```

When `spring-boot-starter-actuator` is removed from the dependencies, which gets `WebMvcMetricsFilter` out of the picture, request is processed as expected:

```shell
$ http -f POST :8080/pv/post name='Vedran Pavić' Content-Type:'application/x-www-form-urlencoded'
HTTP/1.1 200 
Content-Length: 19
Content-Type: text/plain;charset=UTF-8
Date: Thu, 11 Jan 2018 17:59:32 GMT

Hello Vedran Pavić
```
