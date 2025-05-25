
[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[AWS_BADGE]:https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white
[POSTGRES_BADGE]:https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white


<h1 align="center" style="font-weight: bold;">Url Shortener💻</h1>

![AWS][AWS_BADGE]
![spring][SPRING_BADGE]
![java][JAVA_BADGE]
![postgres][POSTGRES_BADGE]

<p align="center">
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
</p>

<p align="center">
  <b>System for shortening links with Docker, Traefik as reverse proxy, and a system that identifies shortened URLs based on the domain and URl structure.</b>
</p>

<h2 id="started">🚀 Getting started</h2>

1. Clone the repository.
2. Configure environment variables as described below.
3. docker-compose up
4. Access the API at http://short.local

<h3>Prerequisites</h3>

- [Java 21]()
- [Spring]()
- [Docker]()
- [AWS]()

<h3>Cloning</h3>

How to clone your project

```bash
git clone https://github.com/PedroAntonio2510/url-shortener.git
```

<h3> Environment Variables</h3>

Create a `.env` file in the root folder of the project with the following environment variables:
```yaml
AWS_ACESS_KEY_ID={YOUR_AWS_ACESS_KEY}
AWS_SECRET_ACESS_KEY={YOUR_SECRET_ACESS_KEY}
AWS_REGION={YOUR_AWS_REGION}
AWS_BUCKET_NAME={YOUR_AWS_BUCKET_NAME}
```
Use the `application.properties.example` as reference to create your configuration file `application-prod.properties` with your AWS Credentials

```yaml
aws.s3.bucket-name=${AWS_BUCKET_NAME}
aws.s3.region=${AWS_REGION}
aws.acess-key-id=${AWS_ACESS_KEY_ID}
aws.secret-acess-key=${AWS_SECRET_ACESS_KEY}
```

<h3>Starting</h3>

How to start your project

```bash
cd url-shortener
docker build -t url-shortener:1.0 . 
docker compose up
``````


<h2 id="routes">📍 API Endpoints</h2>

<h3 id="post-auth-detail">POST /api/shorten</h3>

**REQUEST**
```json
{
  "url": "https://exemplo.com/minha-url"
}
```

**RESPONSE**
```json
{
  "short_url": "http://short.local/abc123",
  "original_url": "https://exemplo.com/minha-url"
}
```

<h3 id="get-auth-detail">GET /api/links</h3>

**RESPONSE**
```json
[
  {
    "short_url": "http://short.local/abc123",
    "original_url": "https://exemplo.com",
    "clicks": 42
  },
  {
    "short_url": "http://short.local/xyz789",
    "original_url": "https://outra.com",
    "clicks": 15
  }
]
```
<h3 id="get-auth-detail">DELETE /api/links/{short_code}</h3>

**RESPONSE**
```json
{
  "message": "Link deleted successfully"
}
```
