package iqTask.moneyConverter.controller;

import iqTask.moneyConverter.database.DataBase;
import iqTask.moneyConverter.helper.ConvertationHelper;
import iqTask.moneyConverter.objects.ConvertHistory;
import iqTask.moneyConverter.objects.Valute;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class ValuteController {

    @Resource
    private ConvertHistory convertHistory;

    @Resource
    private ConvertationHelper convertationHelper;

    @Resource
    private DataBase dataBase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String save(@RequestBody ConvertHistory convertHistory) throws SQLException {
        List<ConvertHistory> list = new ArrayList<>();
        list.add(convertHistory);

        Double result = convertationHelper.Convertation(Integer.parseInt(convertHistory.getAmount()), convertHistory.getFirstName(), convertHistory.getSecondName());

        String formateedResult = new DecimalFormat("#0.00").format(result);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(new Date());

        dataBase.addToHistory(date, convertHistory.getFirstName(), convertHistory.getSecondName(), convertHistory.getAmount(), formateedResult);

        return formateedResult;
    }

    @GetMapping("/history")
    public List<ConvertHistory> historyList() throws SQLException {
        List<ConvertHistory> list = dataBase.getHistory();
        return list;
    }

    @GetMapping
    public List<Valute> list() throws SQLException {
        List<Valute> list = dataBase.getCurrentValCurs();
        if ( list.isEmpty() ) {
            dataBase.addCurrentValCursToDb();
            List<Valute> list2 = dataBase.getCurrentValCurs();
            return list2;
        } else {
            return list;
        }
    }
}


