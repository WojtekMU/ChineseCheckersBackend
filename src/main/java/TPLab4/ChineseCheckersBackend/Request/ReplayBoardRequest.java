package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class ReplayBoardRequest
{
    @NotBlank
    private Long historyId;

    public Long getHistoryId()
    {
        return historyId;
    }

    public void setHistoryId(Long historyId)
    {
        this.historyId = historyId;
    }
}
