    #loại bỏ các tập tin xử lý thừa
    -dontpreverify
    #Chỉ định để đóng gói lại tất cả các tập tin lớp được đổi tên,
    -repackageclasses ''
    #Chỉ định truy cập của các lớp và các thành viên lớp có thể được mở rộng trong khi biên dịch
    -allowaccessmodification
    #Chỉ định tối ưu hóa
    -optimizations !code/simplification/arithmetic
    -keepattributes *Annotation*
    #Giữ lại class
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
    }
    #Các class được bảo mật
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }
    -keepclassmembers class * extends android.content.Context {
       public void *(android.view.View);
       public void *(android.view.MenuItem);
    }
    -keepclassmembers class * implements android.os.Parcelable {
        static ** CREATOR;
    }
    -keepclassmembers class **.R$* {
        public static <fields>;
    }
    -keepclassmembers class * {
        @android.webkit.JavascriptInterface <methods>;
    }