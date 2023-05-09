---
部署
---

# 1.PACKAGE
```base
清理 打包 跳过测试
mvn clean package -Dmaven.test.skip=true

清理
mvn clean

删除trie-service-1.0.0-distribution.zip
mvn clean -Pdist
```

# 2.DOCKER