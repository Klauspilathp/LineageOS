package E:.eclipseWorkSpace.myproject.gnol_springboot.sharding;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;

/**
 * @Title: ExtShardingCityDao
 * @Package: E:.eclipseWorkSpace.myproject.gnol_springboot.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:28:26
 * @Description: ShardingCity扩展 Dao
 */
@Repository(value = "extShardingCityDao")
public interface ExtShardingCityDao {

	/**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年08月03日 14:28:26
     * @Description: 根据条件分页查询列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

	/**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年08月03日 14:28:26
     * @Description: 根据编号软删除
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

}