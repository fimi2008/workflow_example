package top.lionxxw.flowabledemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lionxxw.flowabledemo.dao.ApplyWinBillDao;
import top.lionxxw.flowabledemo.entity.ApplyWinBill;
import top.lionxxw.flowabledemo.enums.BusinessKey;
import top.lionxxw.flowabledemo.service.ApplyWinService;
import top.lionxxw.flowabledemo.service.WorkFlowService;

import java.util.List;

/**
 * TODO

 * created on 2018/2/27 9:13
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Service
public class ApplyWinServiceImpl implements ApplyWinService {
    @Autowired
    private ApplyWinBillDao applyWinBillDao;
    @Autowired
    private WorkFlowService workFlowService;

    @Override
    public void createOrUpdate(ApplyWinBill bill) {
        if (null == bill.getId()){
            bill.setStatus(0);
            applyWinBillDao.insert(bill);
            workFlowService.startProcess(bill.getId(), BusinessKey.APPLY_WIN_BILL);
        }else {
            applyWinBillDao.update(bill);
        }
    }

    @Override
    public List<ApplyWinBill> findByUserId(Long userId) {
        return  applyWinBillDao.findByUserId(userId);
    }

    @Override
    public ApplyWinBill getById(Long id) {
        return applyWinBillDao.getById(id);
    }

    @Override
    public List<ApplyWinBill> findAll() {
        return applyWinBillDao.findAll();
    }
}
