# 1-1-1 文档  常规markDown语法写法


#   通用标题
## 通用标题
### 通用标题
#### 通用标题
##### 通用标题
###### 通用标题


# 列表

- 列表一
- 列表二

1. 列表一
2. 列表二


>我是引用文字

*  我是斜体

# 图片显示


[点击我去百度](http://www.baidu.com)

![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/qrcode_for_gh_363af0fc9423_430.jpg "在这里输入图片标题")

# 表格写法

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |


## 代码框

```
@requires_authorization
def somefunc(param1='', param2=0):
    '''A docstring'''
    if param1 > param2: # interesting
        print 'Greater'
    return (param2 - param1 + 1) or None
class SomeClass:
    pass
>>> message = '''interpreter
```








