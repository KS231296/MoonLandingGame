clc
close all
%%testData=load('C:\Users\gendasai\Desktop\Dane2.txt');
testData=load('C:\Users\gendasai\Desktop\LandingChartsData.txt');
figure
subplot(3,1,1)
plot(testData(:,1),testData(:,2)); %% po³o¿enie w czasie
subplot(3,1,2)
plot(testData(:,1),testData(:,3));  %% prêdkoœæ w czasie

subplot(3,1,3)
plot(testData(:,1),testData(:,4));  %% masa w czasie


figure 
plot(testData(:,2),testData(:,3)); %wykres fazowy