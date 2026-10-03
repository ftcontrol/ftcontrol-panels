#!/usr/bin/env sed -EH -f
s/id\("dev\.frozenmilk\.android-library"\) version ".+"/id("dev.frozenmilk.android-library") version "12.0.0-1.2.2"/
s/id\("dev\.frozenmilk\.publish"\) version ".+"/id("dev.frozenmilk.publish") version "0.1.0"/
s/id\("dev\.frozenmilk\.doc"\) version ".+"/id("dev.frozenmilk.doc") version "0.1.0"/
s/id\("dev\.frozenmilk\.build-meta-data"\) version ".+"/id("dev.frozenmilk.build-meta-data") version "0.1.0"/
