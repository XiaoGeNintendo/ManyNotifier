# Many Notifier
Notice you when a new submission comes!
# How to use
You just need to open the application in command line with at least **Java 8** and you can see the submission and details shown in the right-bottom corner of your computer. It will update automatically. You may need to connect to the Internet. 
# Steps 
1. Download the code/jar
    1. If you download the code, open the project in Eclipse and attach all the files in **lib** folder then compile and make it a jar file
2. Create a file called "user.txt" in the same folder as your jar file then turn to <a href="#Configure">Configure User.txt</a>
3. Run the jar file with the following command: `java -jar <jarname>.jar` with your Internet open
4. The solution and its information will show in the right

<h1 id="Configure">Configure user.txt</h1>

In each line of **user.txt** you need to input as the following format: `a=b` where `a` means the name of OJ and the `b` means the regex of the username you want to watch of the `a` OJ. For example, if you write `POJ=vjudge1|vjudge2` then only `vjudge1` and `vjudge2`'s solutions on POJ will be shown to you. **The cases of letters does matter**,for example "Poj" doesn't equal "POJ".There is an example in Github

# Supported Online Judge
| OJ | Prob# | Sub# | Verdict | Test# | Cnt |
| :-: |:-: | :-: | :-: | :-: | :-: |
|Codeforces|Y|N|Y|Y|10|
|POJ|Y|Y|Y|N|20|
|Atcoder(1)|Y|N|Y|Y|20|
|FZU|Y|Y|Y|N|15|
|MYSBZ|Y|Y|Y|N|20|
|HRBUST|Y|Y|Y|N|30|

OJ - The online judge we supported

Prob# -Will Problem ID of the submission display?

Sub# - Will Submission ID of the submission display?

Verdict - Will the verdict of the submission display?

Test# - Will the test case of the submission display? (e.g: Wrong Answer on test 1 on Codeforces)

Cnt - The submissions we update per second

## Notice
1. You need two lines in "user.txt" one is "Atcoder" to specialize the user regex and the other is "AtcoderContest" for the contests you are going to watch. The contest name must be the **short name** of Atcoder contests, for examples AtcoderGrandContest001 should be written as agc001. For example, Atcoder Regular Contest 100's domain is : `https://beta.atcoder.jp/contests/arc100/` then you should input `arc100` which is after `contests`.("AtcoderContest=arc100"). **Atcoder Notifier  doesn't work during contests (will give you 404)** 

# Changelog
- Version 0.7 :Supports **Codeforces** now. Thanks for Mike for the wonderful Codeforces API.
- Version 1.0 :Supports **Peking University Online Judge(POJ)** now and released on Github
- Version 1.1 :Supports **Atcoder** now and move to XiaoGeNintendo's space.
- Version 1.2 :Supports **Fuzhou University Online Judge(FZU)** now and fixed some bugs
- Version 1.3 :Supports **Dashiye Online Judge(MYSBZ)** now!
- Version 1.4 :Supports **Hrbust Online Judge(HRBUST)** now!