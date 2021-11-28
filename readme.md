# 1 并发模型

- 线程与锁机制
- 函数式编程
- 标识和状态分离
- Actor
- CSP Communicating Sequential Processes
- GPU并行
- Lambda MapReduce流式处理

# 2 Akka

Akka提供了一种简单、单一的编程模型，一种并发和分布式应用的编程方式，Actor编程模型。

## 2.1 ActorSystem

提供了如何使用Actor、配置Actor、Actor连接网络、Actor调度或构建Actor集群。

- 管理调度服务
- 配置相关参数
- 日志功能

![actor top tree](https://doc.akka.io/docs/akka/current/typed/guide/diagrams/actor_top_tree.png)

## 2.2 Message

是简单的数据结构，创建后不变更改，是不可变的immutable。 消息可以由本地线程进行处理，也可以由远程服务进行处理。

## 2.3 MessageBox

与一个Actor绑定，它是一个FIFO列队，邮箱保证与之绑定的Actor每次只处理一个消息。 调度线程每次从MessageBox中取一个Message，发送给对应Actor处理。

## 2.4 Actor

是处理消息的单元。 每次收到消息时，它会执行一些动作。执行是异步的。 Actor也可以向其他Actor的邮箱发送消息。

Actor 是由状态（State）、行为（Behavior）和邮箱（MailBox，消息队列）三部分组成

## 2.5 ActorRef

定位Actor，可以查找本地的Actor和远程的Actor。

## 3 Actor Test

可以通过TestActorRef查看Actor的状态

- SilentActor Acto r的执行不能从外界直接观察 [SilentActorSpec.scala](actor/src/test/scala/org/example/actor/SilentActorSpec.scala)
- SendingActor
  当收到消息处理完后，向其他Actor发送消息 [SendingActorSpec.scala](actor/src/test/scala/org/example/actor/SendingActorSpec.scala)
- SideEffectingActor
  接收消息并与其他常规对象以某种方法交互 [SideEffectingActorSpec.scala](actor/src/test/scala/org/example/actor/SideEffectingActorSpec.scala)