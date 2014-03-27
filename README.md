# AutoHideTabsView


AutoHideTabsView give you an easy way to add a auto hide tabs view so that the vision of user get wider


---

## Supported Views

AutoHideTabsView support for any View that ViewPager support

---

## Dependency

Depend on [ViewPagerIndicator] (https://github.com/yangweigbh/Android-ViewPagerIndicator)

---

## Usage and Integration
define layout in Xml:
```xml
<com.yangwei.autohidetabsview.AutoHideTabsView 
	xmlns:android="http://schemas.android.com/apk/res/android"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:id="@+id/mainLayout" />
```


```java
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	mView = (AutoHideTabsView) findViewById(R.id.mainLayout);
	mView.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
}
```

or Create in code
```java
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	mView = new AutoHideTabsView(this);
	setContentView(mView);
	mView.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
}
```



## License

    Copyright 2014 Yang Wei

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.