# Code analysis
## SonarQube CNES Report 
#### Branch %
#### Version 5.0.2 

**By: default**

*Date: 2026-04-01*

*Analyzed the: 2026-04-01*

## Introduction
This document contains results of the code analysis of SonarQube CNES Report

CNES app/plugin for SonarQube that allows users to export analysis reports as OpenXML, Markdown and CSV.

## Configuration

- Quality Profiles
    - Names: Sonar way [Azure Resource Manager]; Sonar way [CloudFormation]; Sonar way [C#]; Sonar way [CSS]; Sonar way [Docker]; Sonar way [Flex]; Sonar way [Go]; Sonar way [IPython Notebooks]; Sonar way [Java]; Sonar way [JavaScript]; Sonar way [JSON]; Sonar way [JSP]; Sonar way [Kotlin]; Sonar way [Kubernetes]; Sonar way [PHP]; Sonar way [Python]; Sonar way [Ruby]; Sonar way [Rust]; Sonar way [Scala]; Sonar way [Secrets]; Sonar way [Terraform]; Sonar way [Text]; Sonar way [TypeScript]; Sonar way [VB.NET]; Sonar way [HTML]; Sonar way [XML]; Sonar way [YAML]; 
    - Files: 75e5b670-4c8d-4384-b82b-93cac13dfd0c.json; 460f67d0-5b8e-4f01-8f6d-877ecaaa31cd.json; 4e1af514-07ef-4f2b-8347-0b78f5a297a4.json; 8e3cf0d8-01fc-4be3-a958-af8b769d4e69.json; f4c7b2b6-223a-4b4a-ad46-d35b5444621b.json; e92cbcc3-1ec7-430b-8b1e-8c8f83406782.json; 1dcd1d56-7dd0-4f38-9d07-983b046ce054.json; a69945b6-7d64-4113-ba1a-1e1d51fbb6ed.json; bbf485b5-26d5-4eda-a46a-1bd81cc76941.json; ec89cc75-ec0a-4cad-b04b-979239365ecc.json; 980025b3-3f49-4a68-8611-110443458712.json; d8fdc25d-5564-4be4-b79b-0efc7dd1fc20.json; bc306941-443d-4d7d-b1fb-20d3cadff307.json; 381849c5-d7ef-4665-8b19-aff918a81287.json; f2cb61aa-c41f-4eb8-b47e-a96e41728951.json; d31dd1ee-f6ac-4394-995b-31c69a9ae52d.json; 86faa8a1-c679-4684-ad3a-fb5583a70747.json; 1bc22a32-df14-4324-acfc-041f92b8f5c6.json; 2e988154-0f64-46e5-b733-ffd8f7f9e944.json; b4589ea4-8920-485e-bf1c-257607fb4bda.json; f45ecfe6-b251-4f4f-ada4-4e6a92aa4909.json; e7e6dcdd-8a63-4762-b45e-0e92aa4ad2b3.json; 812ecbc7-d5fc-4eec-b98c-90069ba3a568.json; 31814655-d0a2-474d-9f2a-0725cafe8144.json; d2cd5ad3-1004-4d5c-b48e-be66753d09e4.json; b5145228-bf0c-4b5e-9207-86d6725416e5.json; 12694264-dd45-44a3-86d7-a522a70ec404.json; 


 - Quality Gate
    - Name: Sonar way
    - File: Sonar way.xml

## Synthesis

### Analysis Status

Reliability | Security | Security Review | Maintainability |
:---:|:---:|:---:|:---:
A | A | E | A |

### Quality gate status

| Quality Gate Status | OK |
|-|-|



### Metrics

Coverage | Duplications | Comment density | Median number of lines of code per file | Adherence to coding standard |
:---:|:---:|:---:|:---:|:---:
83.1 % | 0.0 % | 32.5 % | 39.5 | 99.6 %

### Tests

Total | Success Rate | Skipped | Errors | Failures |
:---:|:---:|:---:|:---:|:---:
129 | 100.0 % | 0 | 0 | 0

### Detailed technical debt

Reliability|Security|Maintainability|Total
---|---|---|---
-|-|0d 2h 4min|0d 2h 4min


### Metrics Range

\ | Cyclomatic Complexity | Cognitive Complexity | Lines of code per file | Coverage | Comment density (%) | Duplication (%)
:---|:---:|:---:|:---:|:---:|:---:|:---:
Min | 0.0 | 0.0 | 1.0 | 0.0 | 0.0 | 0.0
Max | 891.0 | 497.0 | 5571.0 | 100.0 | 75.0 | 0.0

### Volume

Language|Number
---|---
Java|5571
Total|5571


## Issues

### Issues count by severity and types

Type / Severity|INFO|MINOR|MAJOR|CRITICAL|BLOCKER
---|---|---|---|---|---
BUG|0|0|0|0|0
VULNERABILITY|0|0|0|0|0
CODE_SMELL|0|9|8|3|0


### Issues List

Name|Description|Type|Severity|Number
---|---|---|---|---
Cognitive Complexity of methods should not be too high||CODE_SMELL|CRITICAL|3
Methods should not have too many parameters||CODE_SMELL|MAJOR|1
Unused assignments should be removed||CODE_SMELL|MAJOR|5
Two branches in a conditional structure should not have exactly the same implementation||CODE_SMELL|MAJOR|1
"Stream.toList()" method should be used instead of "collectors" when unmodifiable list needed||CODE_SMELL|MAJOR|1
Field names should comply with a naming convention||CODE_SMELL|MINOR|1
Local variable and method parameter names should comply with a naming convention||CODE_SMELL|MINOR|2
"toString()" should never be called on a String object||CODE_SMELL|MINOR|2
"@Deprecated" code should not be used||CODE_SMELL|MINOR|1
Collection contents should be used||CODE_SMELL|MINOR|1
Pattern Matching for "instanceof" operator should be used instead of simple "instanceof" + cast||CODE_SMELL|MINOR|2


## Security Hotspots

### Security hotspots count by category and priority

Category / Priority|LOW|MEDIUM|HIGH
---|---|---|---
LDAP Injection|0|0|0
Object Injection|0|0|0
Server-Side Request Forgery (SSRF)|0|0|0
XML External Entity (XXE)|0|0|0
Insecure Configuration|0|0|0
XPath Injection|0|0|0
Authentication|0|0|0
Weak Cryptography|0|0|0
Denial of Service (DoS)|0|0|0
Log Injection|0|0|0
Cross-Site Request Forgery (CSRF)|0|0|0
Open Redirect|0|0|0
Permission|0|0|0
SQL Injection|0|0|0
Encryption of Sensitive Data|0|0|0
Traceability|0|0|0
Buffer Overflow|0|0|0
File Manipulation|0|0|0
Code Injection (RCE)|0|0|0
Cross-Site Scripting (XSS)|0|0|0
Command Injection|0|0|0
Path Traversal Injection|0|0|0
HTTP Response Splitting|0|0|0
Others|1|0|0


### Security hotspots

Category|Name|Priority|Severity|Count
---|---|---|---|---
Others|Using publicly writable directories is security-sensitive|LOW|CRITICAL|1

