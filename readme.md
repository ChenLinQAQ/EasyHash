# EasyHash

## 1. 需求背景

<b>这是一个从实际生活中引出的App。</b>在参加今年山东省ICPC省赛地时候，要求小组左侧和后方录像，并录屏，并提交MD5值。为了方便，我们决定采用手机录屏，谨慎的我测试了一下HarmonyOS自带录屏软件，发现8分钟就有大约600MB了，如果录5个小时，手机不得瘫痪......于是我找了一个第三方软件，将帧率和画质跳到最低，最后5个小时只有2GB。但是粗心的队友说，iOS的录屏是压缩过的，就没有测试了。后来录完之后发现有44GB......

这么大的文件，几乎不可能在要求的时间内上传的。于是队友想到了，手机上应该也可进行MD5运算，于是搜了一下应用市场，发现并没有......

最终我们放弃提交了这个录屏文件，但是也启发着我做了一个Hash的App。

## 2. 具体实现

### 2.1 大文件MD5计算：

Java原生支持MD5等Hash算法，甚至支持RSA等公钥加密算法。但是发现原生的MD5算法不能支持传入字节流，但是我们的文件很大，不可能一次性全部读入到内存，然后拼接成byte数组，然后计算。于是发现可以使用Apache的commons-codec包，提供的用于摘要运算、编码解码的包。常见的编码解码工具Base64、MD5、Hex、SHA1、DES等。

```java
// 添加依赖
implementation group: 'commons-codec', name: 'commons-codec', version: '1.14'

// 使用方法，传入字节流
String MD5 = DigestUtils.md5Hex(字节流);
String SHA1 = DigestUtils.sha1Hex(字节流);
```

### 2.2 使用了一点面向对象思想

这样就符合OOP的OCP原则了，当新拓展算法的时候，只需要再实现Hash接口就可以了。

<img src="https://s2.loli.net/2022/06/09/PRcfyS91lZx7HDB.png" alt="image.png" style="zoom: 67%;" />

### 2.3 打开文件

```java
// 需要申请一下权限
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

// 打开一个Intent
Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
intent.addCategory(Intent.CATEGORY_OPENABLE);
intent.setType("*/*");   // 可以通过这个属性设置过滤出地文件，如 'image/*'只保存图片
startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);

// 在声明周期函数中监听返回，并获取到Uri
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == FILECHOOSER_RESULTCODE) {
        // 获取到Uri
        Uri result = data == null || resultCode != MainActivity.RESULT_OK ? null : data.getData();
        // 根据获取到的Uri，解析得文件输入流
        InputStream in = this.getContentResolver().openInputStream(result);
    }
}
```

### 2.4 可复制的TextView

```java
// 设置为可选择
android:textIsSelectable="true"
```



## 3. 结果展示

<img src="https://s2.loli.net/2022/06/09/AaBfgpXY2wP4rd1.jpg" alt="BA2D031CC61ABB17DB0FC67F6B862575.jpg" style="zoom: 50%;" />



