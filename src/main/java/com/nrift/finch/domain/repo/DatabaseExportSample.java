package com.nrift.finch.domain.repo;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import com.google.common.collect.ImmutableList;

public class DatabaseExportSample {
    public static void main(String[] args) throws Exception {
        // database connection
        Class driverClass = Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:oracle:thin:@172.16.29.92:1521:d11gr21", "finch_dev", "finch_dev");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        //exportMenuData(connection);
        //exportDbdData(connection);
        //exportSampleData(connection);
        exportSubActionData(connection);
    }
    
    private static void exportMenuData(IDatabaseConnection connection) throws Exception {
        QueryDataSet dataSet = new QueryDataSet(connection);
        ImmutableList<String> tables = ImmutableList.of(
                "INF_LANGUAGE",
                "INF_CHARSET",
                "INF_COMPONENT",
                "INF_ENTERPRISE",
                "INF_ENTERPRISE_NAME_XREF",
                "INF_BRANCH",
                "INF_BRANCH_NAME_XREF",
                "INF_EMPLOYEE",
                "INF_EMPLOYEE_NAME_XREF",
                "INF_APPLICATION_ROLE",
                "INF_EMP_APPLN_ROLE_PARTICIPANT",
                "INF_ACTION",
                "INF_ACTION_ROLE_PARTICIPANT",
                "INF_ACTION_CONSOLIDATION",
                "INF_CLOSING_STATUS",
                "INF_CONSTRAINT_LIST",
                "INF_CONSTRAINT_VALUES",
                "INF_UI_SCREEN@select * from inf_ui_screen connect by prior screen_key = next_screen_pk start with next_screen_pk is null",
                "INF_UI_MENU@select * from inf_ui_menu connect by prior menu_pk = menu_parent_pk start with menu_parent_pk is null",
                "INF_UI_MENU_NAME_CROSS_REF",
                "INF_UI_MENU_ROLE_PARTICIPANT",
                "INF_UI_SCREEN_NAME_CROSS_REF");
        
        for (String table : tables) {
            String sp[] = table.split("@");
            if(sp.length == 1)
                dataSet.addTable(table);
            else 
                dataSet.addTable(sp[0], sp[1]);
        }
        
        FlatDtdDataSet.write(dataSet, new FileWriter("finch-inf-test-data.dtd"));
        FlatXmlDataSet.write(dataSet, new FileOutputStream("finch-inf-test-data.xml"));
    }
    
    private static void exportSubActionData(IDatabaseConnection connection) throws Exception {
        QueryDataSet dataSet = new QueryDataSet(connection);
        ImmutableList<String> tables = ImmutableList.of(
                
                "INF_ACTION_CONSOLIDATION");
        
        for (String table : tables) {
            String sp[] = table.split("@");
            if(sp.length == 1)
                dataSet.addTable(table);
            else 
                dataSet.addTable(sp[0], sp[1]);
        }
        
        //FlatDtdDataSet.write(dataSet, new FileWriter("finch-inf-test-data.dtd"));
        FlatXmlDataSet.write(dataSet, new FileOutputStream("finch-inf-sub-action-test-data.xml"));
    }
    
    private static void exportDbdData(IDatabaseConnection connection) throws Exception {
        QueryDataSet dataSet = new QueryDataSet(connection);
        ImmutableList<String> tables = ImmutableList.of(
                
                
                "DBD_WIDGET_TYPE_EMP_CONFIG"

                );
        
        for (String table : tables) {
            dataSet.addTable(table);
        }
        
        FlatXmlDataSet.write(dataSet, new FileOutputStream("dbd_task_type_cfg.xml"));
    }
    
    private static void exportSampleData(IDatabaseConnection connection) throws Exception {
        QueryDataSet dataSet = new QueryDataSet(connection);
        ImmutableList<String> tables = ImmutableList.of(
                
                
                
"REF_REFERENCE_NUMBER"

                );
        
        for (String table : tables) {
            dataSet.addTable(table);
        }
        
        FlatXmlDataSet.write(dataSet, new FileOutputStream("sample2.xml"));
    }
}
