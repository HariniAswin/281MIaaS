CREATE TABLE `Cloud` (
  `name` varchar(100) NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  `usageIndex` float DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Host` (
  `name` varchar(100) NOT NULL,
  `os` varchar(100) DEFAULT NULL,
  `totalCPUUnits` float DEFAULT NULL,
  `totalRam` float DEFAULT NULL,
  `totalStorage` float DEFAULT NULL,
  `cpuAllocated` float DEFAULT NULL,
  `ramAllocated` float DEFAULT NULL,
  `storageAllocated` float DEFAULT NULL,
  `cloudName` varchar(100) NOT NULL,
  `requests` int(11) DEFAULT NULL,
  `resourceType` varchar(100) NOT NULL,
  `externalResource` tinyint(1) DEFAULT '0',
  `tenantId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `cloudName` (`cloudName`),
  CONSTRAINT `host_ibfk_1` FOREIGN KEY (`cloudName`) REFERENCES `Cloud` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ResourceRequestAllocation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `resourceType` varchar(100) NOT NULL,
  `os` varchar(100) DEFAULT NULL,
  `cpu` float DEFAULT NULL,
  `ram` float DEFAULT NULL,
  `storage` float DEFAULT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `assignedCloud` varchar(100) DEFAULT NULL,
  `assignedHost` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `externalResource` tinyint(1) DEFAULT '0',
  `externalResourceId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `assignedCloud` (`assignedCloud`),
  KEY `assignedHost` (`assignedHost`),
  CONSTRAINT `resourcerequestallocation_ibfk_1` FOREIGN KEY (`assignedCloud`) REFERENCES `Cloud` (`name`),
  CONSTRAINT `resourcerequestallocation_ibfk_2` FOREIGN KEY (`assignedHost`) REFERENCES `Host` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;