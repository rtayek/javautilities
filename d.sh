files=`cat histograms.txt`
files=`cat mainguifiles.txt`
#echo $files
n=1
for file in $files
	do
	echo $n $file `basename $file`
	newname=H$n.`basename $file`
	echo newname: $newname
	#ls -l $file
	cp $file $newname
	n=$(($n+1))
	done
exit
for file in $files
	do
	for file2 in $files
		do
		if [ $file != $file2 ]
			then
			echo $file vs $file2
			if diff -w $file $file2
				then echo $file vs $file2 are the same
				else echo different
			fi
			echo -----------------------------------------
		fi
		done
	done