# WebServer
## 新人任务


### 简介

1.基于java实现   
2.采用reactor设计模式   
3.运用了select作为IO多路复用机制   
4.服务器包含四个类，分别是：   
    Reactor（反应器）   
    Acceptor（接收器）   
    SocketReadHandler（处理请求，找到所请求的静态文件）  
    SocketRequestHandler（创造响应，发送文件）  
5.可用文件有index.html和index2.html   


### 感想 
1.本次任务从看不懂题目到最终实现，经历了网上找资料学习阻塞与非阻塞、IO多路复用、socket通信的概念，
  到（如获至宝地）找到了别人的python简易服务器代码却发现并不是socket api实现的，到再一次（如获至
  宝地）找到了别人的python代码却发现不是非阻塞的，最终找到java中的SocketChannel类，根据《Java高
  级特性》、《Http权威指南》和网上reactor设计模式教程自己尝试实现了。  
2.一开始用的是百度，因为觉得vpn太麻烦，现在已经了解了谷歌的好处了。  
 
 
 ### 不足
 1.因为一开始啥都不懂，浪费了很多时间  
 2.任务中所提到的其他模式还没来得及看  
 3.不会arch（泪），因此不知道能不能在linux上用  
 4.SocketReadHandler只初步处理了请求，还需完善  
 5.应该把各种类的run方法里的一大串功能分成小一点的方法(写完才发现)  
