[1mdiff --git a/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyHelper.java b/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyHelper.java[m
[1mindex 4ecb917..1929d48 100644[m
[1m--- a/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyHelper.java[m
[1m+++ b/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyHelper.java[m
[36m@@ -1,33 +1,60 @@[m
 package com.fada21.android.filipa30bday.io.helpers;[m
 [m
 import android.content.Context;[m
[31m-import android.graphics.drawable.Drawable;[m
[32m+[m[32mimport android.view.MenuItem;[m
 [m
[32m+[m[32mimport com.fada21.android.filipa30bday.R;[m
[32m+[m
[32m+[m[32mimport lombok.Getter;[m
 import lombok.NonNull;[m
[31m-import lombok.RequiredArgsConstructor;[m
[32m+[m[32mimport lombok.Setter;[m
 [m
[31m-@RequiredArgsConstructor(suppressConstructorProperties = true)[m
 public class DittyHelper {[m
[32m+[m
[32m+[m[32m    public static final int SHOW_DITTY_LEVEL_ON = 0;[m
[32m+[m[32m    public static final int SHOW_DITTY_LEVEL_OFF = 1;[m
[32m+[m
     @NonNull[m
     private final Context context;[m
     @NonNull[m
[31m-    private final Drawable dittyDrawable;[m
[32m+[m[32m    private final MenuItem menuItem;[m
[32m+[m
[32m+[m[32m    @Getter[m
[32m+[m[32m    @Setter[m
[32m+[m[32m    private boolean doShowDitty;[m
[32m+[m
[32m+[m[32m    public DittyHelper(Context context, MenuItem menuItem) {[m
[32m+[m[32m        this.context = context;[m
[32m+[m[32m        this.menuItem = menuItem;[m
[32m+[m[32m        doShowDitty = DittyStaticHelper.doShowDitties(context);[m
[32m+[m[32m    }[m
 [m
     public boolean doShowDitties() {[m
[31m-        return DittyStaticHelper.doShowDitties(context);[m
[32m+[m[32m        return doShowDitty;[m
     }[m
 [m
     public void setShowDitties(boolean doShowDitties) {[m
         DittyStaticHelper.setShowDitties(context, doShowDitties);[m
[32m+[m[32m        setDoShowDitty(doShowDitties);[m
     }[m
 [m
     public boolean toggleShowDitty() {[m
[31m-        return DittyStaticHelper.toggleShowDitty(context);[m
[32m+[m[32m        setDoShowDitty(DittyStaticHelper.toggleShowDitty(context));[m
[32m+[m[32m        return doShowDitties();[m
     }[m
 [m
[31m-    public void setShowDittyIconLevel() {[m
[31m-        DittyStaticHelper.setShowDittyIconLevel(dittyDrawable, doShowDitties());[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Do on UI thread.[m
[32m+[m[32m     */[m
[32m+[m[32m    public void setupActionBar() {[m
[32m+[m[32m        boolean doShowDitties = doShowDitties();[m
[32m+[m[32m        if (doShowDitties) {[m
[32m+[m[32m            menuItem.setTitle(context.getString(R.string.action_show_ditty_off));[m
[32m+[m[32m            menuItem.getIcon().setLevel(SHOW_DITTY_LEVEL_OFF);[m
[32m+[m[32m        } else {[m
[32m+[m[32m            menuItem.setTitle(context.getString(R.string.action_show_ditty_on));[m
[32m+[m[32m            menuItem.getIcon().setLevel(SHOW_DITTY_LEVEL_ON);[m
[32m+[m[32m        }[m
     }[m
 [m
[31m-[m
 }[m
[1mdiff --git a/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyStaticHelper.java b/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyStaticHelper.java[m
[1mindex 01def78..fd8ba19 100644[m
[1m--- a/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyStaticHelper.java[m
[1m+++ b/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/io/helpers/DittyStaticHelper.java[m
[36m@@ -1,16 +1,12 @@[m
 package com.fada21.android.filipa30bday.io.helpers;[m
 [m
 import android.content.Context;[m
[31m-import android.graphics.drawable.Drawable;[m
 import android.preference.PreferenceManager;[m
 [m
 import com.fada21.android.filipa30bday.utils.FilipCoverAppConsts;[m
 [m
 public class DittyStaticHelper {[m
 [m
[31m-    public static final int SHOW_DITTY_LEVEL_ON = 0;[m
[31m-    public static final int SHOW_DITTY_LEVEL_OFF = 1;[m
[31m-[m
     public static boolean doShowDitties(Context context) {[m
         return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(FilipCoverAppConsts.SHOW_DITTY, false);[m
     }[m
[36m@@ -22,15 +18,7 @@[m [mpublic class DittyStaticHelper {[m
     public static boolean toggleShowDitty(Context context) {[m
         boolean newShowDitties = !doShowDitties(context);[m
         setShowDitties(context, newShowDitties);[m
[31m-        return  newShowDitties;[m
[31m-    }[m
[31m-[m
[31m-    public static void setShowDittyIconLevel(Drawable drawable, boolean showDitty) {[m
[31m-        if (showDitty) {[m
[31m-            drawable.setLevel(SHOW_DITTY_LEVEL_OFF);[m
[31m-        } else {[m
[31m-            drawable.setLevel(SHOW_DITTY_LEVEL_ON);[m
[31m-        }[m
[32m+[m[32m        return newShowDitties;[m
     }[m
 [m
 }[m
[1mdiff --git a/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/ui/activities/FilipActivity.java b/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/ui/activities/FilipActivity.java[m
[1mindex 8138e3f..d103f91 100644[m
[1m--- a/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/ui/activities/FilipActivity.java[m
[1m+++ b/Filipa30BDay/app/src/main/java/com/fada21/android/filipa30bday/ui/activities/FilipActivity.java[m
[36m@@ -67,8 +67,7 @@[m [mpublic class FilipActivity extends ActionBarActivity {[m
         getMenuInflater().inflate(R.menu.menu_filip, menu);[m
 [m
         MenuItem showDitty = menu.findItem(R.id.action_toggle_ditty);[m
[31m-        dittyIconDrawable = showDitty.getIcon();[m
[31m-        dittyHelper = new DittyHelper(this, dittyIconDrawable);[m
[32m+[m[32m        dittyHelper = new DittyHelper(this, showDitty);[m
 [m
         return super.onCreateOptionsMenu(menu);[m
     }[m
[36m@@ -76,7 +75,7 @@[m [mpublic class FilipActivity extends ActionBarActivity {[m
 [m
     @Override[m
     public boolean onPrepareOptionsMenu(Menu menu) {[m
[31m-        dittyHelper.setShowDittyIconLevel();[m
[32m+[m[32m        dittyHelper.setupActionBar();[m
         return super.onPrepareOptionsMenu(menu);[m
     }[m
 [m
[36m@@ -117,7 +116,7 @@[m [mpublic class FilipActivity extends ActionBarActivity {[m
 [m
     public void onEventMainThread(EventShowDittyToggle ev) {[m
         boolean doShowDitty = dittyHelper.toggleShowDitty();[m
[31m-        dittyHelper.setShowDittyIconLevel();[m
[32m+[m[32m        dittyHelper.setupActionBar();[m
         EventBus.getDefault().post(new EventShowDittyOnToggled(doShowDitty));[m
     }[m
 }[m
[1mdiff --git a/Filipa30BDay/app/src/main/res/values-pl/strings.xml b/Filipa30BDay/app/src/main/res/values-pl/strings.xml[m
[1mindex d5a73c0..9d24178 100644[m
[1m--- a/Filipa30BDay/app/src/main/res/values-pl/strings.xml[m
[1m+++ b/Filipa30BDay/app/src/main/res/values-pl/strings.xml[m
[36m@@ -8,5 +8,6 @@[m
 [m
     <string name="share_wishes">Filip ma 30 lat! Warto wspomnieć jego sukcesy. Pamiętam jak wczoraj jego udział w =%s=</string>[m
 [m
[32m+[m[32m    <string name="ditty_author_label">autor: %s</string>[m
 [m
 </resources>[m
\ No newline at end of file[m
[1mdiff --git a/Filipa30BDay/app/src/main/res/values/strings.xml b/Filipa30BDay/app/src/main/res/values/strings.xml[m
[1mindex 3df29f8..2e15812 100644[m
[1m--- a/Filipa30BDay/app/src/main/res/values/strings.xml[m
[1m+++ b/Filipa30BDay/app/src/main/res/values/strings.xml[m
[36m@@ -5,9 +5,12 @@[m
 [m
     <string name="action_share">Share</string>[m
     <string name="action_full_screen">Full screen</string>[m
[32m+[m
     <string name="action_show_ditty_on">Show ditty</string>[m
     <string name="action_show_ditty_off">Hide ditty</string>[m
 [m
     <string name="share_wishes">Filip made it to his thirties. Great success! Let\'s endorse one of his best performances in =%s=</string>[m
 [m
[32m+[m[32m    <string name="ditty_author_label">by %s</string>[m
[32m+[m
 </resources>[m
