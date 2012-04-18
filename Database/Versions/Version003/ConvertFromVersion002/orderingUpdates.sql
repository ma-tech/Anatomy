
            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 1
              where REL_OID = 11682;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11682,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 2
              where REL_OID = 12956;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12956,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 3
              where REL_OID = 12960;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12960,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 4
              where REL_OID = 12963;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12963,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 5
              where REL_OID = 11728;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11728,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 6
              where REL_OID = 11749;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11749,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 7
              where REL_OID = 11752;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11752,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 8
              where REL_OID = 11914;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11914,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 9
              where REL_OID = 11770;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11770,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 10
              where REL_OID = 11756;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11756,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 11
              where REL_OID = 11917;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11917,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 12
              where REL_OID = 12022;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12022,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 13
              where REL_OID = 12030;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12030,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 14
              where REL_OID = 12038;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12038,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 14
              where REL_OID = 27042;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27042,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 15
              where REL_OID = 12046;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12046,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 16
              where REL_OID = 12055;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12055,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 17
              where REL_OID = 11759;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11759,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 18
              where REL_OID = 12064;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12064,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 18
              where REL_OID = 26952;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26952,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 18
              where REL_OID = 26991;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26991,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 19
              where REL_OID = 29714;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29714,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 19
              where REL_OID = 31487;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31487,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 20
              where REL_OID = 29715;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29715,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 20
              where REL_OID = 31488;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31488,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 21
              where REL_OID = 11935;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11935,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 22
              where REL_OID = 11939;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11939,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 23
              where REL_OID = 29716;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29716,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 23
              where REL_OID = 31489;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31489,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 24
              where REL_OID = 11946;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11946,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 25
              where REL_OID = 11949;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11949,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 26
              where REL_OID = 11953;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11953,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 27
              where REL_OID = 11957;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11957,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 28
              where REL_OID = 11762;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11762,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 29
              where REL_OID = 11766;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11766,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 30
              where REL_OID = 31294;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31294,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 31
              where REL_OID = 12100;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12100,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 32
              where REL_OID = 27024;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27024,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 32
              where REL_OID = 29720;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29720,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 32
              where REL_OID = 31492;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31492,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 33
              where REL_OID = 29721;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29721,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 33
              where REL_OID = 31493;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31493,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 34
              where REL_OID = 12130;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12130,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 35
              where REL_OID = 12137;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12137,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 36
              where REL_OID = 27025;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27025,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 36
              where REL_OID = 29722;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29722,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 36
              where REL_OID = 31494;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31494,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 37
              where REL_OID = 12152;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12152,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 38
              where REL_OID = 12159;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12159,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 39
              where REL_OID = 12167;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12167,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 40
              where REL_OID = 12175;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12175,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 41
              where REL_OID = 10388;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10388,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 42
              where REL_OID = 10406;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10406,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 43
              where REL_OID = 10466;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10466,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 44
              where REL_OID = 10495;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10495,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 45
              where REL_OID = 10472;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10472,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 46
              where REL_OID = 10476;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10476,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 47
              where REL_OID = 10481;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10481,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 48
              where REL_OID = 10485;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10485,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 49
              where REL_OID = 10491;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10491,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 50
              where REL_OID = 10427;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10427,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 51
              where REL_OID = 31282;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31282,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 52
              where REL_OID = 31284;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31284,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 53
              where REL_OID = 31283;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31283,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 54
              where REL_OID = 31285;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31285,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 55
              where REL_OID = 10457;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10457,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 55
              where REL_OID = 26959;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26959,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 56
              where REL_OID = 29711;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29711,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 57
              where REL_OID = 10512;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10512,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 58
              where REL_OID = 10515;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10515,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 59
              where REL_OID = 10518;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10518,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 60
              where REL_OID = 10521;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10521,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 61
              where REL_OID = 10525;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10525,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 62
              where REL_OID = 10553;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10553,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 62
              where REL_OID = 26960;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26960,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 63
              where REL_OID = 10562;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10562,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 63
              where REL_OID = 26961;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26961,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 64
              where REL_OID = 10533;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10533,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 65
              where REL_OID = 10540;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10540,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 66
              where REL_OID = 10544;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10544,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 67
              where REL_OID = 10549;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10549,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 68
              where REL_OID = 10530;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10530,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 69
              where REL_OID = 10536;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     10536,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 70
              where REL_OID = 31286;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31286,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 71
              where REL_OID = 11961;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11961,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 72
              where REL_OID = 11964;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11964,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 73
              where REL_OID = 29717;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29717,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 73
              where REL_OID = 31490;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31490,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 74
              where REL_OID = 12183;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12183,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 75
              where REL_OID = 12190;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12190,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 76
              where REL_OID = 27033;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27033,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 76
              where REL_OID = 27062;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27062,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 76
              where REL_OID = 29723;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29723,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 76
              where REL_OID = 31495;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31495,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 77
              where REL_OID = 11971;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11971,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 78
              where REL_OID = 11975;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11975,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 79
              where REL_OID = 11978;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11978,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 80
              where REL_OID = 11981;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11981,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 81
              where REL_OID = 11984;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11984,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 81
              where REL_OID = 31499;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31499,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 82
              where REL_OID = 29718;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29718,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 82
              where REL_OID = 31491;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31491,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 83
              where REL_OID = 11990;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11990,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 84
              where REL_OID = 11993;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11993,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 85
              where REL_OID = 11996;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11996,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 86
              where REL_OID = 11999;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11999,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 87
              where REL_OID = 12010;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12010,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 88
              where REL_OID = 29719;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29719,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 89
              where REL_OID = 11774;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11774,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 90
              where REL_OID = 11777;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11777,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 91
              where REL_OID = 12717;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12717,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 91
              where REL_OID = 26992;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26992,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 92
              where REL_OID = 11781;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11781,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 93
              where REL_OID = 12967;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12967,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 93
              where REL_OID = 26965;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26965,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 94
              where REL_OID = 12205;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12205,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 95
              where REL_OID = 12213;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12213,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 96
              where REL_OID = 12227;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12227,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 96
              where REL_OID = 27044;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27044,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 97
              where REL_OID = 12220;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12220,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 97
              where REL_OID = 27043;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27043,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 98
              where REL_OID = 12234;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12234,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 98
              where REL_OID = 26981;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26981,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 99
              where REL_OID = 26970;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26970,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 99
              where REL_OID = 26999;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26999,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 99
              where REL_OID = 27063;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27063,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 99
              where REL_OID = 29724;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29724,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 99
              where REL_OID = 31496;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31496,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 100
              where REL_OID = 12248;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12248,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 101
              where REL_OID = 12256;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12256,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 101
              where REL_OID = 26982;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26982,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 102
              where REL_OID = 12263;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12263,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 102
              where REL_OID = 27045;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27045,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 103
              where REL_OID = 12270;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12270,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 104
              where REL_OID = 31295;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31295,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 105
              where REL_OID = 12278;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12278,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 106
              where REL_OID = 12286;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12286,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 107
              where REL_OID = 12293;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12293,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 108
              where REL_OID = 31296;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31296,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 109
              where REL_OID = 12301;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12301,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 109
              where REL_OID = 26983;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26983,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 110
              where REL_OID = 12305;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12305,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 111
              where REL_OID = 12309;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12309,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 112
              where REL_OID = 27006;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27006,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 112
              where REL_OID = 29730;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29730,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 112
              where REL_OID = 31498;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31498,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 113
              where REL_OID = 12400;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12400,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 114
              where REL_OID = 12405;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12405,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 115
              where REL_OID = 12409;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12409,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 115
              where REL_OID = 27050;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27050,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 116
              where REL_OID = 12413;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12413,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 117
              where REL_OID = 12417;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12417,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 118
              where REL_OID = 12422;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12422,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 119
              where REL_OID = 12427;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12427,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 120
              where REL_OID = 12431;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12431,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 121
              where REL_OID = 12436;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12436,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 122
              where REL_OID = 12440;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12440,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 122
              where REL_OID = 31517;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31517,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 123
              where REL_OID = 12444;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12444,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 124
              where REL_OID = 12448;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12448,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 125
              where REL_OID = 12452;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12452,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 125
              where REL_OID = 27051;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27051,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 125
              where REL_OID = 31518;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31518,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 126
              where REL_OID = 26971;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26971,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 126
              where REL_OID = 27000;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27000,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 126
              where REL_OID = 27005;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27005,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 126
              where REL_OID = 27034;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27034,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 126
              where REL_OID = 27064;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27064,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 126
              where REL_OID = 29725;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29725,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 126
              where REL_OID = 31497;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31497,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 127
              where REL_OID = 12321;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12321,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 128
              where REL_OID = 12326;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12326,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 129
              where REL_OID = 12344;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12344,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 130
              where REL_OID = 12348;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12348,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 131
              where REL_OID = 12352;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12352,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 132
              where REL_OID = 29726;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29726,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 133
              where REL_OID = 29727;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29727,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 134
              where REL_OID = 29728;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29728,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 135
              where REL_OID = 12357;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12357,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 136
              where REL_OID = 12361;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12361,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 137
              where REL_OID = 12365;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12365,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 138
              where REL_OID = 12370;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12370,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 139
              where REL_OID = 12375;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12375,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 139
              where REL_OID = 31519;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31519,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 140
              where REL_OID = 12379;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12379,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 141
              where REL_OID = 12333;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12333,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 142
              where REL_OID = 12466;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12466,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 142
              where REL_OID = 27048;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27048,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 143
              where REL_OID = 26984;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26984,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 143
              where REL_OID = 29729;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29729,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 144
              where REL_OID = 12385;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12385,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 145
              where REL_OID = 12472;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12472,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 146
              where REL_OID = 12478;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12478,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 147
              where REL_OID = 12482;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12482,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 148
              where REL_OID = 12486;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12486,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 149
              where REL_OID = 12492;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12492,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 150
              where REL_OID = 12496;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12496,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 151
              where REL_OID = 12500;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12500,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 152
              where REL_OID = 31297;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31297,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 153
              where REL_OID = 12388;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12388,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 154
              where REL_OID = 12391;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12391,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 155
              where REL_OID = 12459;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12459,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 155
              where REL_OID = 27015;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27015,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 156
              where REL_OID = 12511;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12511,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 157
              where REL_OID = 12586;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12586,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 158
              where REL_OID = 12603;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12603,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 159
              where REL_OID = 29731;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29731,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 160
              where REL_OID = 29732;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29732,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 161
              where REL_OID = 29733;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29733,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 162
              where REL_OID = 29734;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29734,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 163
              where REL_OID = 29736;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29736,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 164
              where REL_OID = 29737;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29737,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 165
              where REL_OID = 31298;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31298,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 165
              where REL_OID = 31502;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31502,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 166
              where REL_OID = 12607;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12607,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 167
              where REL_OID = 12611;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12611,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 168
              where REL_OID = 12625;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12625,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 169
              where REL_OID = 12616;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12616,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 170
              where REL_OID = 29738;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29738,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 171
              where REL_OID = 29739;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29739,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 172
              where REL_OID = 12631;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12631,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 172
              where REL_OID = 27052;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27052,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 173
              where REL_OID = 12635;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12635,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 173
              where REL_OID = 27053;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27053,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 174
              where REL_OID = 12643;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12643,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 174
              where REL_OID = 31500;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31500,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 175
              where REL_OID = 12647;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12647,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 176
              where REL_OID = 12652;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12652,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 177
              where REL_OID = 12656;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12656,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 178
              where REL_OID = 12660;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12660,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 179
              where REL_OID = 12665;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12665,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 180
              where REL_OID = 12670;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12670,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 180
              where REL_OID = 27054;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27054,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 181
              where REL_OID = 12679;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12679,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 181
              where REL_OID = 31501;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31501,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 182
              where REL_OID = 12556;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12556,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 183
              where REL_OID = 12562;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12562,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 184
              where REL_OID = 12568;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12568,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 185
              where REL_OID = 31302;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31302,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 186
              where REL_OID = 12532;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12532,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 186
              where REL_OID = 27014;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27014,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 187
              where REL_OID = 31291;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31291,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 188
              where REL_OID = 12541;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12541,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 188
              where REL_OID = 26985;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26985,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 189
              where REL_OID = 12544;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12544,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 190
              where REL_OID = 31292;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31292,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 191
              where REL_OID = 31293;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31293,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 192
              where REL_OID = 12547;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12547,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 193
              where REL_OID = 12550;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12550,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 194
              where REL_OID = 31287;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31287,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 195
              where REL_OID = 31288;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31288,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 196
              where REL_OID = 31290;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31290,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 197
              where REL_OID = 27047;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27047,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 197
              where REL_OID = 31289;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31289,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 198
              where REL_OID = 13132;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13132,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 198
              where REL_OID = 26986;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26986,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 199
              where REL_OID = 29747;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29747,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 200
              where REL_OID = 29748;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29748,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 201
              where REL_OID = 29749;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29749,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 202
              where REL_OID = 13140;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13140,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 202
              where REL_OID = 26987;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26987,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 203
              where REL_OID = 29751;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29751,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 204
              where REL_OID = 29752;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29752,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 205
              where REL_OID = 29753;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29753,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 206
              where REL_OID = 29763;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29763,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 207
              where REL_OID = 31314;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31314,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 208
              where REL_OID = 31315;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31315,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 209
              where REL_OID = 31318;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31318,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 210
              where REL_OID = 13096;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13096,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 211
              where REL_OID = 13075;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13075,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 212
              where REL_OID = 13082;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13082,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 213
              where REL_OID = 13088;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13088,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 214
              where REL_OID = 31320;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31320,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 215
              where REL_OID = 31316;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31316,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 216
              where REL_OID = 13012;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13012,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 217
              where REL_OID = 13017;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13017,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 218
              where REL_OID = 29743;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29743,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 219
              where REL_OID = 31321;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31321,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 220
              where REL_OID = 29744;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29744,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 221
              where REL_OID = 29745;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29745,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 222
              where REL_OID = 13119;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13119,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 223
              where REL_OID = 13123;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13123,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 224
              where REL_OID = 31319;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31319,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 225
              where REL_OID = 13029;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13029,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 226
              where REL_OID = 13034;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13034,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 227
              where REL_OID = 31317;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31317,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 228
              where REL_OID = 13024;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13024,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 229
              where REL_OID = 31322;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31322,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 230
              where REL_OID = 13144;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13144,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 231
              where REL_OID = 29762;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29762,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 232
              where REL_OID = 13154;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13154,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 233
              where REL_OID = 12800;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12800,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 234
              where REL_OID = 12838;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12838,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 235
              where REL_OID = 12845;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12845,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 236
              where REL_OID = 12852;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12852,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 237
              where REL_OID = 31323;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31323,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 238
              where REL_OID = 31328;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31328,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 239
              where REL_OID = 31329;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31329,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 240
              where REL_OID = 31330;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31330,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 241
              where REL_OID = 31331;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31331,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 242
              where REL_OID = 12859;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12859,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 243
              where REL_OID = 12866;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12866,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 244
              where REL_OID = 12873;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12873,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 245
              where REL_OID = 31324;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31324,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 246
              where REL_OID = 12880;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12880,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 247
              where REL_OID = 12887;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12887,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 248
              where REL_OID = 12894;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12894,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 249
              where REL_OID = 31325;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31325,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 250
              where REL_OID = 12908;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12908,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 251
              where REL_OID = 12915;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12915,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 252
              where REL_OID = 12922;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12922,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 253
              where REL_OID = 31326;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31326,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 254
              where REL_OID = 12935;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12935,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 255
              where REL_OID = 12941;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12941,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 256
              where REL_OID = 12947;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12947,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 257
              where REL_OID = 31327;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31327,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 258
              where REL_OID = 12929;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12929,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 259
              where REL_OID = 31347;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31347,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 260
              where REL_OID = 31348;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31348,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 261
              where REL_OID = 31349;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31349,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 262
              where REL_OID = 31350;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31350,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 263
              where REL_OID = 31351;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31351,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 264
              where REL_OID = 31352;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31352,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 265
              where REL_OID = 13161;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13161,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 266
              where REL_OID = 13168;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13168,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 267
              where REL_OID = 13176;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13176,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 268
              where REL_OID = 13183;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13183,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 269
              where REL_OID = 13190;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13190,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 270
              where REL_OID = 13197;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13197,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 271
              where REL_OID = 13231;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13231,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 272
              where REL_OID = 13235;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13235,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 273
              where REL_OID = 13239;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13239,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 274
              where REL_OID = 31332;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31332,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 275
              where REL_OID = 31333;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31333,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 276
              where REL_OID = 31334;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31334,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 277
              where REL_OID = 31335;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31335,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 278
              where REL_OID = 31336;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31336,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 279
              where REL_OID = 31337;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31337,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 280
              where REL_OID = 31338;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31338,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 281
              where REL_OID = 31339;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31339,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 282
              where REL_OID = 29754;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29754,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 283
              where REL_OID = 29755;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29755,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 284
              where REL_OID = 29756;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29756,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 285
              where REL_OID = 29757;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29757,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 286
              where REL_OID = 13202;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13202,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 287
              where REL_OID = 13217;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13217,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 288
              where REL_OID = 13246;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13246,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 289
              where REL_OID = 13253;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13253,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 290
              where REL_OID = 13261;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13261,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 291
              where REL_OID = 13268;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13268,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 292
              where REL_OID = 13275;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13275,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 293
              where REL_OID = 31340;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31340,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 294
              where REL_OID = 31343;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31343,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 295
              where REL_OID = 31344;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31344,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 296
              where REL_OID = 31345;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31345,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 297
              where REL_OID = 29761;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29761,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 298
              where REL_OID = 31341;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31341,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 299
              where REL_OID = 31342;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31342,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 300
              where REL_OID = 31353;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31353,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 301
              where REL_OID = 31354;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31354,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 302
              where REL_OID = 12739;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12739,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 303
              where REL_OID = 12978;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12978,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 304
              where REL_OID = 12981;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12981,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 305
              where REL_OID = 12985;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12985,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 306
              where REL_OID = 31303;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31303,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 307
              where REL_OID = 31304;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31304,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 308
              where REL_OID = 31305;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31305,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 309
              where REL_OID = 31306;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31306,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 310
              where REL_OID = 31307;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31307,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 311
              where REL_OID = 31308;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31308,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 312
              where REL_OID = 31309;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31309,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 313
              where REL_OID = 31310;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31310,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 314
              where REL_OID = 31311;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31311,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 315
              where REL_OID = 31312;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31312,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 316
              where REL_OID = 31313;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31313,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 317
              where REL_OID = 11692;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     11692,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 318
              where REL_OID = 29712;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29712,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 319
              where REL_OID = 29713;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29713,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 320
              where REL_OID = 12789;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12789,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 321
              where REL_OID = 12733;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     12733,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 322
              where REL_OID = 29982;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29982,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 323
              where REL_OID = 29979;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29979,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 324
              where REL_OID = 29980;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29980,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 325
              where REL_OID = 31516;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31516,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 326
              where REL_OID = 26990;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26990,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 327
              where REL_OID = 26969;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26969,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 328
              where REL_OID = 26972;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26972,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 328
              where REL_OID = 31299;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31299,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 329
              where REL_OID = 26951;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26951,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 330
              where REL_OID = 27013;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27013,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 330
              where REL_OID = 31544;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31544,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 331
              where REL_OID = 27041;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27041,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 332
              where REL_OID = 26980;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26980,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 333
              where REL_OID = 29981;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29981,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 334
              where REL_OID = 29983;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29983,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 335
              where REL_OID = 13147;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13147,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 336
              where REL_OID = 31346;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31346,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 337
              where REL_OID = 31486;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31486,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 338
              where REL_OID = 13315;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13315,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 339
              where REL_OID = 13323;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13323,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 340
              where REL_OID = 13328;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13328,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 341
              where REL_OID = 13333;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13333,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 342
              where REL_OID = 13337;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13337,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 343
              where REL_OID = 13341;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13341,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 344
              where REL_OID = 13685;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13685,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 345
              where REL_OID = 29898;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29898,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 346
              where REL_OID = 29899;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29899,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 347
              where REL_OID = 31455;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31455,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 348
              where REL_OID = 31457;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31457,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 349
              where REL_OID = 29900;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29900,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 350
              where REL_OID = 31450;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31450,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 351
              where REL_OID = 31451;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31451,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 352
              where REL_OID = 13344;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13344,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 353
              where REL_OID = 13349;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13349,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 354
              where REL_OID = 13969;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13969,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 355
              where REL_OID = 13975;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13975,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 356
              where REL_OID = 13972;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13972,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 357
              where REL_OID = 31475;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31475,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 358
              where REL_OID = 31477;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31477,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 359
              where REL_OID = 31456;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31456,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 360
              where REL_OID = 31476;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31476,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 361
              where REL_OID = 31474;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31474,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 362
              where REL_OID = 13978;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13978,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 362
              where REL_OID = 26966;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     26966,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 363
              where REL_OID = 13981;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13981,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 364
              where REL_OID = 13319;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13319,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 365
              where REL_OID = 31355;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31355,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 366
              where REL_OID = 31452;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31452,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 367
              where REL_OID = 31453;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31453,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 368
              where REL_OID = 29901;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29901,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 369
              where REL_OID = 29903;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29903,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 370
              where REL_OID = 29902;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29902,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 371
              where REL_OID = 31454;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31454,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 372
              where REL_OID = 29904;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29904,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 373
              where REL_OID = 29906;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29906,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 374
              where REL_OID = 29905;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29905,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 375
              where REL_OID = 29910;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29910,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 376
              where REL_OID = 29911;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29911,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 377
              where REL_OID = 29912;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29912,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 378
              where REL_OID = 29907;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29907,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 379
              where REL_OID = 29908;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29908,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 380
              where REL_OID = 29909;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29909,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 381
              where REL_OID = 13715;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13715,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 382
              where REL_OID = 29848;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29848,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 383
              where REL_OID = 29849;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29849,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 384
              where REL_OID = 29850;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29850,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 385
              where REL_OID = 29852;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29852,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 386
              where REL_OID = 29851;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29851,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 387
              where REL_OID = 29853;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29853,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 388
              where REL_OID = 29854;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29854,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 389
              where REL_OID = 29860;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29860,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 390
              where REL_OID = 29861;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29861,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 391
              where REL_OID = 29855;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29855,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 392
              where REL_OID = 29856;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29856,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 393
              where REL_OID = 29862;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29862,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 394
              where REL_OID = 29863;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29863,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 395
              where REL_OID = 29864;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29864,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 396
              where REL_OID = 29865;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29865,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 397
              where REL_OID = 29866;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29866,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 398
              where REL_OID = 29867;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29867,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 399
              where REL_OID = 13742;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13742,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 400
              where REL_OID = 13745;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13745,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 401
              where REL_OID = 29857;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29857,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 402
              where REL_OID = 29858;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29858,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 403
              where REL_OID = 29859;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29859,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 404
              where REL_OID = 29871;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29871,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 405
              where REL_OID = 29872;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29872,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 406
              where REL_OID = 29873;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29873,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 407
              where REL_OID = 29869;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29869,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 408
              where REL_OID = 29870;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29870,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 409
              where REL_OID = 29875;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29875,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 410
              where REL_OID = 29876;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29876,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 411
              where REL_OID = 13960;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13960,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 411
              where REL_OID = 31523;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31523,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 412
              where REL_OID = 29891;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29891,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 413
              where REL_OID = 29892;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29892,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 414
              where REL_OID = 29893;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29893,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 415
              where REL_OID = 29894;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29894,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 416
              where REL_OID = 29895;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29895,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 417
              where REL_OID = 29896;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29896,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 418
              where REL_OID = 29897;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29897,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 419
              where REL_OID = 31449;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31449,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 420
              where REL_OID = 29920;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29920,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 421
              where REL_OID = 13966;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13966,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 422
              where REL_OID = 29921;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29921,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 423
              where REL_OID = 29922;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29922,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 424
              where REL_OID = 29923;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29923,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 425
              where REL_OID = 13918;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13918,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 426
              where REL_OID = 29887;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29887,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 427
              where REL_OID = 29888;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29888,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 428
              where REL_OID = 29925;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29925,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 429
              where REL_OID = 29926;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29926,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 430
              where REL_OID = 29927;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29927,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 430
              where REL_OID = 31511;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31511,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 431
              where REL_OID = 29928;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29928,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 431
              where REL_OID = 31515;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31515,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 432
              where REL_OID = 29929;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29929,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 432
              where REL_OID = 31507;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31507,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 433
              where REL_OID = 29930;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29930,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 434
              where REL_OID = 29931;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29931,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 434
              where REL_OID = 31509;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31509,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 435
              where REL_OID = 29932;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29932,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 435
              where REL_OID = 31513;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31513,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 436
              where REL_OID = 29933;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29933,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 436
              where REL_OID = 31505;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31505,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 437
              where REL_OID = 29934;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29934,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 438
              where REL_OID = 29935;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29935,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 438
              where REL_OID = 31510;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31510,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 439
              where REL_OID = 29936;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29936,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 439
              where REL_OID = 31514;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31514,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 440
              where REL_OID = 29937;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29937,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 440
              where REL_OID = 31506;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31506,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 441
              where REL_OID = 31461;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31461,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 442
              where REL_OID = 31462;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31462,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 443
              where REL_OID = 31463;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31463,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 444
              where REL_OID = 31464;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31464,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 445
              where REL_OID = 13833;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13833,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 445
              where REL_OID = 27075;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27075,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 446
              where REL_OID = 29878;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29878,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 447
              where REL_OID = 29877;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29877,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 448
              where REL_OID = 31436;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31436,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 449
              where REL_OID = 31437;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31437,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 450
              where REL_OID = 29879;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29879,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 451
              where REL_OID = 31438;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31438,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 452
              where REL_OID = 31439;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31439,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 453
              where REL_OID = 13923;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13923,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 454
              where REL_OID = 13928;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13928,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 455
              where REL_OID = 29913;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29913,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 456
              where REL_OID = 29914;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29914,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 457
              where REL_OID = 29917;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29917,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 458
              where REL_OID = 29916;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29916,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 459
              where REL_OID = 29918;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29918,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 460
              where REL_OID = 29919;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29919,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 461
              where REL_OID = 29915;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29915,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 462
              where REL_OID = 31458;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31458,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 463
              where REL_OID = 13933;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13933,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 464
              where REL_OID = 29889;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29889,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 465
              where REL_OID = 29890;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29890,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 466
              where REL_OID = 31448;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31448,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 467
              where REL_OID = 29938;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29938,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 468
              where REL_OID = 31465;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31465,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 469
              where REL_OID = 31466;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31466,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 470
              where REL_OID = 31467;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31467,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 471
              where REL_OID = 31468;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31468,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 472
              where REL_OID = 29942;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29942,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 473
              where REL_OID = 29943;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29943,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 474
              where REL_OID = 29944;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29944,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 475
              where REL_OID = 29945;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29945,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 476
              where REL_OID = 29946;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29946,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 477
              where REL_OID = 29947;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29947,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 478
              where REL_OID = 29948;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29948,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 479
              where REL_OID = 29949;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29949,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 480
              where REL_OID = 29950;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29950,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 481
              where REL_OID = 29951;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29951,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 482
              where REL_OID = 29952;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29952,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 483
              where REL_OID = 29953;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29953,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 484
              where REL_OID = 29954;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29954,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 485
              where REL_OID = 29955;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29955,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 486
              where REL_OID = 29956;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29956,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 487
              where REL_OID = 29957;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29957,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 488
              where REL_OID = 29958;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29958,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 489
              where REL_OID = 29959;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29959,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 490
              where REL_OID = 29960;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29960,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 491
              where REL_OID = 29961;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29961,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 492
              where REL_OID = 29962;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29962,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 493
              where REL_OID = 29963;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29963,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 494
              where REL_OID = 29964;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29964,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 495
              where REL_OID = 29965;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29965,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 496
              where REL_OID = 29966;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29966,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 497
              where REL_OID = 29967;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29967,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 498
              where REL_OID = 29968;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29968,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 499
              where REL_OID = 29969;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29969,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 500
              where REL_OID = 29970;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29970,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 501
              where REL_OID = 31469;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31469,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 502
              where REL_OID = 13939;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13939,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 502
              where REL_OID = 27077;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27077,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 503
              where REL_OID = 13693;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13693,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 503
              where REL_OID = 27073;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27073,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 503
              where REL_OID = 31521;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31521,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 504
              where REL_OID = 29832;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29832,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 505
              where REL_OID = 29830;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29830,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 506
              where REL_OID = 29831;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29831,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 507
              where REL_OID = 31430;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31430,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 508
              where REL_OID = 29842;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29842,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 509
              where REL_OID = 29845;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29845,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 510
              where REL_OID = 31428;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31428,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 511
              where REL_OID = 31429;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31429,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 512
              where REL_OID = 29833;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29833,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 513
              where REL_OID = 29843;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29843,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 514
              where REL_OID = 13859;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13859,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 514
              where REL_OID = 31522;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31522,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 515
              where REL_OID = 13864;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13864,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 516
              where REL_OID = 29880;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29880,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 517
              where REL_OID = 29881;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29881,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 518
              where REL_OID = 31440;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31440,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 519
              where REL_OID = 31431;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31431,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 520
              where REL_OID = 31432;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31432,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 521
              where REL_OID = 31433;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31433,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 522
              where REL_OID = 31434;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31434,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 523
              where REL_OID = 29882;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29882,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 524
              where REL_OID = 29834;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29834,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 525
              where REL_OID = 29844;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29844,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 526
              where REL_OID = 29838;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29838,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 527
              where REL_OID = 29840;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29840,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 528
              where REL_OID = 31441;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31441,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 529
              where REL_OID = 13872;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13872,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 530
              where REL_OID = 31445;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31445,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 531
              where REL_OID = 29883;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29883,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 532
              where REL_OID = 29884;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29884,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 533
              where REL_OID = 29885;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29885,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 534
              where REL_OID = 29886;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29886,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 535
              where REL_OID = 31446;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31446,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 536
              where REL_OID = 13869;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13869,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 537
              where REL_OID = 31442;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31442,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 538
              where REL_OID = 31443;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31443,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 539
              where REL_OID = 31444;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31444,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 540
              where REL_OID = 29924;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29924,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 540
              where REL_OID = 31524;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31524,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 541
              where REL_OID = 31459;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31459,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 542
              where REL_OID = 31460;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31460,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 543
              where REL_OID = 29839;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29839,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 544
              where REL_OID = 31427;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31427,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 545
              where REL_OID = 31471;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31471,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 545
              where REL_OID = 31525;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31525,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 546
              where REL_OID = 31472;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31472,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 547
              where REL_OID = 31473;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31473,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 548
              where REL_OID = 31447;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31447,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 549
              where REL_OID = 31435;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31435,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 549
              where REL_OID = 31503;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31503,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 550
              where REL_OID = 31470;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31470,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 551
              where REL_OID = 13358;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13358,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 552
              where REL_OID = 13599;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13599,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 553
              where REL_OID = 13605;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13605,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 554
              where REL_OID = 13640;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13640,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 555
              where REL_OID = 13645;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13645,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 556
              where REL_OID = 13611;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13611,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 557
              where REL_OID = 31403;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31403,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 558
              where REL_OID = 31404;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31404,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 559
              where REL_OID = 31405;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31405,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 560
              where REL_OID = 31406;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31406,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 561
              where REL_OID = 13619;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13619,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 562
              where REL_OID = 13635;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13635,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 563
              where REL_OID = 31407;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31407,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 564
              where REL_OID = 31408;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31408,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 565
              where REL_OID = 13629;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13629,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 566
              where REL_OID = 13625;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13625,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 567
              where REL_OID = 13666;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13666,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 568
              where REL_OID = 13671;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13671,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 569
              where REL_OID = 13676;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13676,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 570
              where REL_OID = 13651;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13651,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 571
              where REL_OID = 13656;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13656,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 572
              where REL_OID = 13661;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13661,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 573
              where REL_OID = 13399;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13399,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 574
              where REL_OID = 13408;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13408,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 575
              where REL_OID = 31360;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31360,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 576
              where REL_OID = 13485;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13485,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 577
              where REL_OID = 29767;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29767,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 578
              where REL_OID = 29768;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29768,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 579
              where REL_OID = 13426;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13426,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 580
              where REL_OID = 13434;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13434,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 581
              where REL_OID = 13441;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13441,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 582
              where REL_OID = 31378;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31378,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 582
              where REL_OID = 31542;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31542,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 583
              where REL_OID = 13475;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13475,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 584
              where REL_OID = 29765;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29765,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 585
              where REL_OID = 29766;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29766,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 586
              where REL_OID = 13494;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13494,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 587
              where REL_OID = 31361;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31361,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 588
              where REL_OID = 31362;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31362,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 589
              where REL_OID = 31363;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31363,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 590
              where REL_OID = 29771;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29771,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 591
              where REL_OID = 29772;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29772,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 592
              where REL_OID = 29773;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29773,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 593
              where REL_OID = 29774;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29774,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 594
              where REL_OID = 29775;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29775,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 595
              where REL_OID = 29776;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29776,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 596
              where REL_OID = 31359;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31359,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 597
              where REL_OID = 31364;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31364,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 598
              where REL_OID = 31365;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31365,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 599
              where REL_OID = 31366;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31366,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 600
              where REL_OID = 31367;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31367,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 601
              where REL_OID = 31368;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31368,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 602
              where REL_OID = 31369;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31369,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 603
              where REL_OID = 31370;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31370,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 604
              where REL_OID = 31371;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31371,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 605
              where REL_OID = 31372;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31372,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 606
              where REL_OID = 31373;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31373,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 607
              where REL_OID = 31374;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31374,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 608
              where REL_OID = 31375;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31375,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 609
              where REL_OID = 31376;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31376,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 610
              where REL_OID = 31377;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31377,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 611
              where REL_OID = 29777;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29777,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 612
              where REL_OID = 29778;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29778,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 613
              where REL_OID = 29779;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29779,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 614
              where REL_OID = 29780;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29780,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 615
              where REL_OID = 29781;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29781,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 616
              where REL_OID = 29782;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29782,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 617
              where REL_OID = 29783;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29783,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 618
              where REL_OID = 29784;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29784,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 619
              where REL_OID = 29770;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29770,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 620
              where REL_OID = 13451;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13451,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 621
              where REL_OID = 13390;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13390,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 621
              where REL_OID = 27088;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27088,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 622
              where REL_OID = 13564;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13564,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 623
              where REL_OID = 13533;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13533,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 624
              where REL_OID = 13507;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13507,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 624
              where REL_OID = 27090;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27090,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 625
              where REL_OID = 13538;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13538,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 626
              where REL_OID = 29792;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29792,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 627
              where REL_OID = 29795;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29795,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 628
              where REL_OID = 29796;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29796,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 629
              where REL_OID = 29793;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29793,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 630
              where REL_OID = 29794;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29794,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 631
              where REL_OID = 29797;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29797,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 632
              where REL_OID = 29798;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29798,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 633
              where REL_OID = 29799;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29799,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 634
              where REL_OID = 29800;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29800,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 635
              where REL_OID = 31397;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31397,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 636
              where REL_OID = 31399;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31399,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 637
              where REL_OID = 31398;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31398,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 638
              where REL_OID = 29803;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29803,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 639
              where REL_OID = 13543;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13543,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 640
              where REL_OID = 29804;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29804,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 641
              where REL_OID = 29805;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29805,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 642
              where REL_OID = 31401;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31401,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 643
              where REL_OID = 31400;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31400,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 644
              where REL_OID = 29820;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29820,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 645
              where REL_OID = 29821;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29821,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 646
              where REL_OID = 29822;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29822,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 647
              where REL_OID = 29823;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29823,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 648
              where REL_OID = 29824;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29824,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 649
              where REL_OID = 29825;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29825,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 650
              where REL_OID = 29826;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29826,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 651
              where REL_OID = 29827;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29827,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 652
              where REL_OID = 29828;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29828,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 653
              where REL_OID = 29829;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29829,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 654
              where REL_OID = 31409;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31409,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 655
              where REL_OID = 13549;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13549,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 656
              where REL_OID = 13559;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13559,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 657
              where REL_OID = 29811;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29811,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 657
              where REL_OID = 31534;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31534,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 658
              where REL_OID = 29812;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29812,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 659
              where REL_OID = 29813;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29813,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 660
              where REL_OID = 29814;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29814,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 660
              where REL_OID = 31537;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31537,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 661
              where REL_OID = 29815;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29815,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 661
              where REL_OID = 31539;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31539,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 662
              where REL_OID = 13554;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13554,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 663
              where REL_OID = 29806;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29806,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 663
              where REL_OID = 31533;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31533,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 664
              where REL_OID = 29807;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29807,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 665
              where REL_OID = 29808;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29808,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 666
              where REL_OID = 29809;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29809,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 666
              where REL_OID = 31536;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31536,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 667
              where REL_OID = 29810;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29810,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 667
              where REL_OID = 31540;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31540,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 668
              where REL_OID = 13520;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13520,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 668
              where REL_OID = 27092;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27092,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 668
              where REL_OID = 31528;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31528,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 669
              where REL_OID = 31390;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31390,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 670
              where REL_OID = 31391;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31391,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 671
              where REL_OID = 31392;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31392,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 672
              where REL_OID = 29790;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29790,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 673
              where REL_OID = 29791;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     29791,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 674
              where REL_OID = 13528;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13528,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 675
              where REL_OID = 31383;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31383,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 676
              where REL_OID = 31386;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31386,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 677
              where REL_OID = 31385;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31385,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 678
              where REL_OID = 31387;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31387,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 679
              where REL_OID = 31384;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31384,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 680
              where REL_OID = 13524;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13524,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 681
              where REL_OID = 31381;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31381,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 682
              where REL_OID = 31382;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31382,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 683
              where REL_OID = 31393;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31393,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 684
              where REL_OID = 31394;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31394,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 685
              where REL_OID = 31395;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31395,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 686
              where REL_OID = 31396;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31396,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 687
              where REL_OID = 31388;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31388,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 688
              where REL_OID = 31389;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31389,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 689
              where REL_OID = 13514;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13514,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 689
              where REL_OID = 27091;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27091,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 689
              where REL_OID = 31527;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31527,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 690
              where REL_OID = 31410;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31410,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 690
              where REL_OID = 31529;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31529,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 691
              where REL_OID = 31379;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31379,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 692
              where REL_OID = 31411;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31411,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 693
              where REL_OID = 31380;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31380,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 694
              where REL_OID = 31520;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31520,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 695
              where REL_OID = 31541;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31541,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 696
              where REL_OID = 31357;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31357,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 696
              where REL_OID = 31543;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31543,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 697
              where REL_OID = 31532;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31532,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 698
              where REL_OID = 31535;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31535,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 699
              where REL_OID = 31504;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31504,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 700
              where REL_OID = 31508;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31508,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 701
              where REL_OID = 31512;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31512,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 702
              where REL_OID = 31412;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31412,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 703
              where REL_OID = 27086;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27086,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 704
              where REL_OID = 27072;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     27072,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 705
              where REL_OID = 31526;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31526,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 706
              where REL_OID = 31402;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31402,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 707
              where REL_OID = 31413;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31413,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 707
              where REL_OID = 31530;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31530,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 708
              where REL_OID = 31414;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31414,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 709
              where REL_OID = 31415;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31415,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 710
              where REL_OID = 31416;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31416,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 711
              where REL_OID = 31417;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31417,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 712
              where REL_OID = 31418;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31418,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 713
              where REL_OID = 31419;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31419,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 714
              where REL_OID = 31420;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31420,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 715
              where REL_OID = 31421;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31421,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 716
              where REL_OID = 13568;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13568,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 717
              where REL_OID = 13572;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13572,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 718
              where REL_OID = 13581;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13581,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 719
              where REL_OID = 13576;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     13576,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 720
              where REL_OID = 31422;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31422,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 720
              where REL_OID = 31531;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31531,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 721
              where REL_OID = 31423;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31423,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 722
              where REL_OID = 31424;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31424,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 723
              where REL_OID = 31425;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31425,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 724
              where REL_OID = 31426;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31426,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 725
              where REL_OID = 31538;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31538,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;


            update ANA_RELATIONSHIP
              set REL_SEQUENCE = 726
              where REL_OID = 31356;


            insert into ANA_LOG
                ( LOG_OID,
                  LOG_VERSION_FK,
                  LOG_LOGGED_OID,
                  LOG_COLUMN_NAME,
                  LOG_OLD_VALUE,
                  LOG_COMMENTS)
              select NULL,
                     VER_OID,
                     31356,
                     "REL_SEQUENCE",
                     NULL,
                     "Replaced w/ sequence from MS-Word files.  See version comments for more."
                from ANA_VERSION
                where VER_DATE = (
                        select MAX(VER_DATE)
                          from ANA_VERSION ) ;

