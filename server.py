import socket
import segmentation
import face_detection

host = ''
port = 8000
 
server_sock = socket.socket(socket.AF_INET)
server_sock.bind((host, port))
server_sock.listen(1)
 
while True:
    print("기다리는 중")
    client_sock, addr = server_sock.accept() #접속한 클라이언트
 
    print('Connected by', addr)
    data = client_sock.recv(1024)
    data = data.decode()
    print(data)
 
    while True:
        # 클라이언트에서 받을 문자열의 길이
        data = client_sock.recv(4)
        length = int.from_bytes(data, "little")
        # 클라이언트에서 문자열 받기
        msg = client_sock.recv(length)
 
        # data를 더 이상 받을 수 없을 때
        if len(data) <= 0:
            break
        """
        msg = msg.decode()
        print(msg,1)
 
        msg = "eco: " + msg
        data = msg.encode()
        """
        #data = str(segmentation.calculation())
        face_detection.play([480, 1800, 2295, 4230, 6090, 8395, 9060, 9159])
        
        data = str([12000, 45000, 57375, 105750, 152250, 209875, 226500, 228975]).encode()
        length = len(data)
        # 클라이언트에 문자열 길이 보내기
        client_sock.sendall(length.to_bytes(4, byteorder="little"))
        # 클라이언트에 문자열 보내기
        client_sock.sendall(data)
 
    client_sock.close()
 
server_sock.close()