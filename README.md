
# JWT有什么好处？

* 1、支持跨域访问: Cookie是不允许垮域访问的，这一点对Token机制是不存在的，前提是传输的用户认证信息通过HTTP头传输.

* 2、无状态(也称：服务端可扩展行):Token机制在服务端不需要存储session信息，因为Token 自身包含了所有登录用户的信息，只需要在客户端的cookie或本地介质存储状态信息.

* 3、去耦: 不需要绑定到一个特定的身份验证方案。Token可以在任何地方生成，只要在你的API被调用的时候，你可以进行Token生成调用即可.（这个似乎也在继续说前面第一点和第二点的好处。。。）

* 4、更适用于移动应用: 当你的客户端是一个原生平台（iOS, Android，Windows 8等）时，Cookie是不被支持的（你需要通过Cookie容器进行处理），这时采用Token认证机制就会简单得多。

* 5、CSRF:因为不再依赖于Cookie，所以你就不需要考虑对CSRF（跨站请求伪造）的防范。（如果token是用cookie保存，CSRF还是需要考虑,一般建议使用1、在HTTP请求中以参数的形式加入一个服务器端产生的token。或者2.放入http请求头中也就是一次性给所有该类请求加上csrftoken这个HTTP头属性，并把token值放入其中）
ps:后面会推出一些常见的网络安全的处理

# JWT的组成

第一部分我们称它为头部（header),第二部分我们称其为载荷（payload, 类似于飞机上承载的物品)，第三部分是签证（signature).

**header**

jwt的头部承载两部分信息：

* 1.声明类型，这里是jwt
* 2声明加密的算法 通常直接使用 HMAC SHA256
完整的头部就像下面这样的JSON：
```
{
  'typ': 'JWT',
  'alg': 'HS256'
}
```
然后将头部进行base64加密（该加密是可以对称解密的),构成了第一部分.

> eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9

**playload**

载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分

定义一个payload:
```
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

然后将其进行base64加密，得到Jwt的第二部分。

> eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9

**signature**

jwt的第三部分是一个签证信息，这个签证信息由三部分组成：

* 1.header (base64后的)
* 2.payload (base64后的)
* 3.secret

这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。
```
// javascript
var encodedString = base64UrlEncode(header) + '.' + base64UrlEncode(payload);

var signature = HMACSHA256(encodedString, 'secret'); // TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
```
将这三部分用.连接成一个完整的字符串,构成了最终的jwt:

> eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ


*注意：secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。*
