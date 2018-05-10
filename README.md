# SimpleZxing
二维码生成、条形码、二维码扫描的简单封装（此库仅做练手使用）
==
1、引用
--
implementation 'org.zero:SimpleZXing:1.0.2'<br>
2、生成二维码
--
Bitmap bitmap = ZxingUtil.getInstance().encodeAsBitmap(MainActivity.this, "hahaha");<br>
3、解析二维码
--
先做摄像头权限申请，然后ZxingUtil.getInstance().decode(MainActivity.this);返回结果在
`"
@Override<br>
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DecodeActivity.REQUEST_CODE_DECODE && requestCode == RESULT_OK) {
            String stringExtra = data.getStringExtra(DecodeActivity.DECODE_RESULT);
            Log.e("", "结果：" + stringExtra);
        }
    }
`"
