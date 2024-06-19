import proto.UserRequests_pb2 as UserRequest
import http.client
import json

login = UserRequest.AuthRequest()

login.username = "papillon@mail.com"
login.password = "papi55"

'''filepath = "login_request.bin"

with open(filepath, 'wb') as f:
    f.write(login.SerializeToString())'''


conn = http.client.HTTPConnection("localhost", 1010)
payload = login.SerializeToString()
headers = {
  'Content-Type': 'application/x-protobuf'
}
conn.request("POST", "/api/public/login", payload, headers)

res = conn.getresponse()
print(f"\n{res.status}\n")
data = res.read()

print(data.decode("utf-8"))
