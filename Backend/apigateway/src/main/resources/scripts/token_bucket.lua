local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local refill_rate = tonumber(ARGV[2])
local now_ms = tonumber(ARGV[3])
local requested = tonumber(ARGV[4])

local data = redis.call("HMGET", key, "tokens", "last_refill_ms")
local tokens = tonumber(data[1])
local last_refill_ms = tonumber(data[2])

if tokens == nil then
    tokens = capacity
    last_refill_ms = now_ms
end

local elapsed_sec = math.max(0, (now_ms - last_refill_ms) / 1000)
local refill = elapsed_sec * refill_rate
tokens = math.min(capacity, tokens + refill)

local allowed = 0
if tokens >= requested then
    tokens = tokens - requested
    allowed = 1
end

redis.call("HMSET", key, "tokens", tostring(tokens), "last_refill_ms", tostring(now_ms))
redis.call("EXPIRE", key, 60)

return allowed