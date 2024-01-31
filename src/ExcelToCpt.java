import com.fr.base.operator.common.CommonOperator;
import com.fr.chart.activator.ChartBaseActivator;
import com.fr.cluster.engine.activator.standalone.StandaloneModeActivator;
import com.fr.config.activator.BaseDBActivator;
import com.fr.config.activator.ConfigurationActivator;
import com.fr.env.operator.CommonOperatorImpl;
import com.fr.general.I18nResource;
import com.fr.health.activator.ModuleHealActivator;
import com.fr.io.ResourceRepositoryActivator;
import com.fr.io.importer.Excel2007ReportImporter;
import com.fr.main.TemplateWorkBook;
import com.fr.main.impl.WorkBook;
import com.fr.module.Module;
import com.fr.module.tool.ActivatorToolBox;
import com.fr.report.ReportActivator;
import com.fr.report.RestrictionActivator;
import com.fr.report.module.ReportBaseActivator;
import com.fr.report.write.WriteActivator;
import com.fr.scheduler.SchedulerActivator;
import com.fr.store.StateServiceActivator;
import com.fr.workspace.simple.SimpleWork;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
public class ExcelToCpt {
    public static void main(String[] args) throws Exception {
        // 首先需要定义执行所在的环境，这样才能正确读取数据库信息
        // 定义报表运行环境,用于执行报表
        Module module = ActivatorToolBox.simpleLink(new BaseDBActivator(),
                new ConfigurationActivator(),
                new ResourceRepositoryActivator(),
                new StandaloneModeActivator(),
                new ModuleHealActivator(),
                new StateServiceActivator(),
                new ChartBaseActivator(),
                new SchedulerActivator(),
                new ReportBaseActivator(),
                new RestrictionActivator(),
                new ReportActivator(),
                new WriteActivator());
        SimpleWork.supply(CommonOperator.class, new CommonOperatorImpl());
        String envpath = "//Applications//FineReport10.0//webapps//webroot//WEB-INF";//工程路径
        SimpleWork.checkIn(envpath);
        I18nResource.getInstance();
        module.start();
        File excelFile = new File("//Users//huzhiqi//Documents//胡智奇//数据//报表//促销活动.xlsx"); // 获取EXCEL文件
        FileInputStream a = new FileInputStream(excelFile);
        TemplateWorkBook tpl = new Excel2007ReportImporter().generateWorkBookByStream(a);
        OutputStream outputStream = new FileOutputStream(new File("//Users//huzhiqi//Documents//胡智奇//数据//报表//promotion_detail.cpt")); // 转换成cpt模板
        ((WorkBook) tpl).export(outputStream);
        outputStream.close();
        module.stop();
    }
}