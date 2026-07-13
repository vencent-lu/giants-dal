# giants-dal

[![Maven Central](https://img.shields.io/maven-central/v/com.github.vencent-lu/giants-dal.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.github.vencent-lu/giants-dal)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![JDK](https://img.shields.io/badge/JDK-1.7%2B-orange.svg)](https://www.oracle.com/java/)

**giants-dal** 是一个基于 Spring 的数据访问层（Data Access Layer）组件，为多种 ORM 框架提供统一、简洁的 DAO 编程模型。

一套 `GiantsDao<T>` 接口，屏蔽底层 ORM 差异，让你在 Hibernate、iBATIS、MyBatis 之间自由选择，甚至在同一个 DAO 里混合使用。

## 特性

- **统一 DAO 接口**：所有 ORM 实现共享同一套 `GiantsDao<T>` CRUD 契约（增删改查、按例查询、命名查询、计数）。
- **多 ORM 支持**：
  - Hibernate（`AbstractHibernateDao`）
  - iBATIS 2.x（`AbstractIbatisDao`）
  - MyBatis（`MybatisDao`）
  - Mix 混合模式（`AbstractMixDao`，同一 DAO 同时使用 Hibernate + iBATIS）
- **泛型实体自动识别**：通过泛型反射自动推断实体类型，无需手动传入 `Class`。
- **iBATIS Spring 集成层**：内置一套完整的 iBATIS 2.x 与 Spring 整合实现（`SqlMapClientTemplate`、`SqlMapClientFactoryBean`、LOB TypeHandler），填补 Spring 3.1 之后移除 iBATIS 支持的空缺。
- **SQL 性能监控**：iBATIS DAO 对每次 SQL 执行计时，超过阈值（默认 200ms）自动告警。
- **批量操作**：iBATIS 模式基于 `startBatch()` 提供真正的批量插入/删除。
- **分库分表（实验性）**：基于 XML 配置的哈希路由框架（`sharding` 包），支持自定义哈希算法。

> ⚠️ **关于分库分表**：`sharding` 模块的路由计算（`DefaultDalShardingRoutingImpl`）和多数据源 DAO（`AbstractMultipleDataSourceIbatisDao`）中的路由逻辑目前尚未完成（部分代码被注释），属于实验性能力，暂不建议用于生产。详见[使用手册](docs/使用手册.md)。

## 环境要求

| 依赖 | 版本 |
| --- | --- |
| JDK | 1.7+ |
| Spring | 4.3.20.RELEASE |
| Hibernate（可选） | 5.3.20.Final |
| iBATIS（可选） | 2.3.4.726 |

Hibernate 与 iBATIS 均声明为 `optional` 依赖，按需引入即可。

## 快速开始

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.github.vencent-lu</groupId>
    <artifactId>giants-dal</artifactId>
    <version>1.0.3</version>
</dependency>
```

### 2. 定义一个 iBATIS DAO

```java
public class UserDao extends AbstractIbatisDao<User> {
    // 无需任何代码，即拥有完整的 CRUD 能力
}
```

### 3. 编写 SqlMap（语句名遵循 `实体类名.操作名` 约定）

```xml
<sqlMap namespace="User">
    <insert id="User.insert" parameterClass="User">
        INSERT INTO t_user (id, name) VALUES (#id#, #name#)
    </insert>
    <select id="User.getById" parameterClass="long" resultClass="User">
        SELECT id, name FROM t_user WHERE id = #value#
    </select>
    <!-- update / delete / getAll / findByExample / getCurrentDatetime ... -->
</sqlMap>
```

### 4. 在 Spring 中装配并使用

```java
User user = new User();
user.setName("giants");
userDao.insert(user);          // 执行 User.insert

User u = userDao.get(1L);      // 执行 User.getById
List<User> all = userDao.loadAll();  // 执行 User.getAll
```

不同 ORM 的接入方式、语句命名约定、Spring 配置示例，请参阅 **[使用手册](docs/使用手册.md)**。

## 核心接口一览

```java
public interface GiantsDao<T> {
    void insert(T entity);
    void update(T entity);
    void insertAll(List<T> entityList);
    void delete(T entity);
    void deleteAll(List<T> entityList);
    T get(Serializable id);
    T load(Serializable id);
    List<T> loadAll();
    Date getCurrentDatetime();
    T findOneEntityByExample(T exampleEntity);
    List<T> findByExample(T exampleEntity);
    List<T> searchForEntityList(String statementName, Object parameterBean);
    int searchForCount(String statementName, Object parameterBean);
}
```

## 模块结构

```
com.giants.dal
├── common          公共常量（DalConstants，iBATIS 语句名约定）
├── dao
│   ├── GiantsDao               统一 DAO 接口
│   ├── hibernate               Hibernate 实现
│   ├── ibatis                  iBATIS 2.x 实现
│   ├── mybatis                 MyBatis 实现接口
│   ├── mix                     Hibernate + iBATIS 混合实现
│   └── datasource              多数据源与分表支持
├── orm.ibatis      iBATIS 2.x 的 Spring 集成层（模板、FactoryBean、LOB TypeHandler）
└── sharding        分库分表路由框架（实验性）
```

## 文档

- 📖 **[使用手册](docs/使用手册.md)** — 各 ORM 详细用法、Spring 配置、命名约定、分库分表说明与常见问题。

## 许可证

本项目基于 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt) 开源。

## 作者

vencent.lu &lt;scsedux@163.com&gt;
