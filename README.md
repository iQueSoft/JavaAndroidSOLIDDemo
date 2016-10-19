# JavaAndroidSOLIDDemo

This application is an example of the SOLID  principles and encapsulation violation. During running it can fall down, don’t do what it should and so on. 
The main task is to refactor the code according to the SOLID principles. 
The idea of this application (what is should do) is described next. 



<b>The Robot</b>

<i>Preconditions:</i> You have got a robot without a battery and charging device, two empty not rechargeable batteries (old type and new type) and unplugged charging device.

<i>Task:</i> Create a program that can control the robot and display an information about it.

<i>Goal:</i> Make the robot to reach the wall in 27 meters from the start and show the level of the battery




<i>The Robot has:</i>

•	Battery

•	Charging device

•	Indicators

<i>The Robot can:</i>

•	Be turned on\off

•	Be on charging

•	Move

•	Reach the destination (set against the wall)

<i>Limitations:</i>

It can’t be turned on without battery or when the battery discharged. It can’t move while charging. When one battery is discharged, you need to change it to another one.


<i>The new type Battery has:</i>

•	Be plugged in\out

•	Level of charge in percent 

•	Charge indicator (lights when fully charged)

<i>The Battery can:</i>

•	Increase its charge level when it is in the robot and the charging device is plugged in(charging speed 20%\sec)

•	Decrease its charge level whet it is plugged in and while moving (the charge level falls down with a speed 5%\m )




<i>The old type Battery has:</i>

•	Level of charge in percent 

<i>The Battery can:</i>

•	Be plugged in\out


•	Increase charge level when it is in the robot and the charging device is plugged in(charging speed 10%\sec)

•	Decrease charge level whet it is plugged in and while moving (the charge level falls down with a speed 5%\m )




<i>The charging device has:</i>

•	Indicator

<i>The charging device can:</i>

•	Be plugged in\out



<b>The program’s UI has:</b>

<i>Indicators:</i>

•	Battery charged\discharged

<i>Status field:</i>

•	Turned on/off

•	Battery missing

•	Battery charge status

•	Robot movement status

•	Reached the destination


<i>Buttons:</i>

•	Turn on\off

•	Plug in new type battery 

•	Plug in old type battery

•	Plug in\out the charging device

•	Move

