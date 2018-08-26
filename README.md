# Many Notifier
Notice you when a new submission comes!
# How to use
You just need to open the application in command line with at least **Java 8** and you can see the submission and details shown in the right-bottom corner of your computer. It will update automatically. You may need to connect to the Internet. 
# Steps 
1. Download the code/jar
    1. If you download the code, open the project in Eclipse and attach all the files in **lib** folder then compile and make it a jar file.
2. Create a file called "config.txt" in the same folder as your jar file then turn to <a href="#Configure">Configure config.txt</a>
3. Run the jar file with the following command: `java -jar <jarname>.jar` with your Internet open
4. The solution and its information will show in the right

<h1 id="Configure">Configure config.txt</h1>

In each line of **config.txt** you need to input as the following format: `a=b` where `a` means the name of OJ and the `b` means the regex of the username you want to watch of the `a` OJ. For example, if you write `POJ=vjudge1|vjudge2` then only `vjudge1` and `vjudge2`'s solutions on POJ will be shown to you. **The cases of letters does matter**,for example "Poj" doesn't equal "POJ". There is an example in Github.

## Other Configurations
Interval=[Integer] : Number of milliseconds between two notifier checks. Default 1000.

DoNotGetFocus=[Boolean] : Do not get focus when new window appears. Note that due to some swing feature, if this mode in on you can't see tooltips. Default false.

ReduceDebug=[Boolean] : Reduce debug info. Mainly reduce debug info from DialogQueue. Default false.

# Supported Online Judge
| OJ | Prob# | Sub# | Verdict | Test# | Score | Cnt |
| :-: |:-: | :-: | :-: | :-: | :-: | :-: |
|Codeforces|Y|N|Y|Y|N|10|
|POJ|Y|Y|Y|N|N|20|
|Atcoder(1)|Y|N|Y|Y|N|20|
|FZU|Y|Y|Y|N|N|15|
|HYSBZ|Y|Y|Y|N|N|20|
|HRBUST|Y|Y|Y|N|N|30|
|ZOJ(2)|Y|Y|Y|N|N|15|
|URAL|Y|N|Y|Y|N|10|
|Luogu|Y(2)|Y|Y|N(3)|Y|20|

OJ - The online judge we supported

Prob# -Will Problem ID of the submission display?

Sub# - Will Submission ID of the submission display?

Verdict - Will the verdict of the submission display?

Test# - Will the test case of the submission display? (e.g: Wrong Answer on test 1 on Codeforces)

Cnt - The submissions we update per second

## Notice
1. You need two lines in "user.txt" one is "Atcoder" to specialize the user regex and the other is "AtcoderContest" for the contests you are going to watch. The contest name must be the **short name** of Atcoder contests, for examples AtcoderGrandContest001 should be written as agc001. For example, Atcoder Regular Contest 100's domain is : `https://beta.atcoder.jp/contests/arc100/` then you should input `arc100` which is after `contests`.("AtcoderContest=arc100"). **Atcoder Notifier  doesn't work during contests (will give you 404)** 
2. ZOJ and Luogu supports Chinese name. Luogu's display font is changed to support Chinese. But as ZOJ doesn't have much Chinese names, we didn't change the display font, so there may be some error characters.
3. The score will be displayed at the place of test case

# Working with the source
Ehh... It is mentioned above. Let me remind you that this is a full Eclipse project, all you need to do is put it in your Eclipse workspace and attach all source jar in the **lib** folder then enjoy.

# Changelog
- Version 0.7 :Supports **Codeforces** now. Thanks for Mike for the wonderful Codeforces API.
- Version 1.0 :Supports **Peking University Online Judge(POJ)** now and released on Github
- Version 1.1 :Supports **Atcoder** now and move to XiaoGeNintendo's space.
- Version 1.2 :Supports **Fuzhou University Online Judge(FZU)** now and fixed some bugs
- Version 1.3 :Supports **Dashiye Online Judge(MYSBZ)** now!
- Version 1.4 :Supports **Hrbust Online Judge(HRBUST)** now!
- Version 1.5 :Supports **Zhejiang University Online Judge(ZOJ)** now and fixed encoder to UTF-8!
- Version 1.6 :Supports **Timus Online Judge(URAL)** now!
- Version 1.7 :Deleted some debug information.
- Version 1.8 :Fixed typo and updates OJ project
- Version 2.0 :Fixed all bugs. Javadoc added. Supports **Luogu** now! Thanks Zzzyt for his huge help!
- Version 2.1 :Fixed typo and added *Working with source* part.