Redis-based rate limiting on your API gateway

Why: Your gateway sits in front of RCU-SERVICE, LOAN-SERVICE, DOCUMENT-SERVICE, and AUTH-USER-SERVICE. Rate limiting protects them (and any third-party APIs like Hunter/Bureau/NSDL downstream) from being overwhelmed by bursts of requests.

Approach: Token bucket algorithm — each client gets a bucket with a max capacity (burst allowance) that refills at a steady rate over time.

Setup, in order:

Redis via Docker — docker run --name redis-local -p 6379:6379 -d redis:7-alpine (one-time). Afterward, just docker start redis-local each session, verify with docker exec -it redis-local redis-cli ping → PONG.
pom.xml — added spring-boot-starter-data-redis.
application.yml — added spring.data.redis (host/port/timeout) and a top-level rate-limit block (capacity, refill-rate). Key lesson: YAML indentation defines structure with no closing braces — a misplaced block silently reads as null/0 with no error.
token_bucket.lua — a Lua script in resources/scripts/. Runs the "read tokens → refill → check → decrement → write" sequence as one atomic operation inside Redis, so concurrent requests can't race each other and both slip through.
RedisRateLimiter.java — Java service that loads and executes the Lua script via StringRedisTemplate.
RateLimitFilter.java — a plain OncePerRequestFilter (not Spring Cloud Gateway's filter DSL, since your gateway uses the newer servlet-based gateway-server-webmvc) that intercepts every request, builds a bucket key per client+API group, and returns 429 when exhausted.
Testing: fire a loop of curl requests at a route — first ~20 return 200, then 429s kick in once the bucket's empty.