# Particles  

Particles is an attempt to reproduce Jeffrey Ventrella's [Clusters](https://ventrella.com/Clusters/) with Java and JavaFx.

I was amazed by the Clusters, and I wanted to reproduce it. However,  I didn't have access to its source code. So, I created everything from scratch, and the Particles took shape.

[Download stand-alone package](https://github.com/zcagdasgurbuz/Particles/releases)

Particles is a simple 2d particle collision / attraction / repulsion simulator. Most of the math that governs Particles' universe is simply "made-up." However, these made-up rules result in very interesting formations as if they are molecules or microscopic creatures. Such as;

https://user-images.githubusercontent.com/45305014/173028545-2b586e69-b053-403b-acae-49b418445cc3.mp4

There are two types of attractions in Particles. The first is gravitational, and the second is what I call molecular. The gravitational force/attraction is only calculated by the distance between particles. The only value that will affect the strength of this attraction is the G constant, which can be changed from the menu.

 On the other hand, the molecular attraction has about 20 different equations, which can be used in 3 ranges.  

1) Two particles are in range when both are in their effective attraction zone. 
2) Two particles are below range when both are closer than their effective attraction zone. 
3) Two particles are out of range when both are outside their effective attraction zone. 

![particles1](https://user-images.githubusercontent.com/45305014/173031107-b08daaa1-6ce2-4716-87c8-43c5fc7b815b.jpg)

![particles2](https://user-images.githubusercontent.com/45305014/173031705-e05c48b7-783a-40ec-b412-ab1e5689cdaa.jpg)

The effective attraction zone is defined by the R value, which can be defined in the menu. Min and max values define the random range of min R and max R. (I know my choice of wordings is not the best! "min R min" ... ughh!?)

![particles3](https://user-images.githubusercontent.com/45305014/173032097-beb71a12-e7f8-4cc6-9d25-e72acd8ceac2.jpg)

### Shortcuts:  
CTRL+R:  Reset    
CTRL+Shift+C:  Capture Mode (removes the menu button)     
F11: Full-screen mode    

## For more, Watch Demo On Youtube

[![Watch the video](https://img.youtube.com/vi/-cw4oDDioGM/maxresdefault.jpg)](https://youtu.be/-cw4oDDioGM?vq=hd1080)


  
### Java Version
Java version : 11   
JavaFx version : 15   
Built by using Maven
