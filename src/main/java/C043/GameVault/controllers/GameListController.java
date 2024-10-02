package C043.GameVault.controllers;

import C043.GameVault.entities.BackLog;
import C043.GameVault.entities.PlayingList;
import C043.GameVault.entities.User;
import C043.GameVault.payloads.GameDTO;
import C043.GameVault.payloads.RespDTO;
import C043.GameVault.security.Validation;
import C043.GameVault.services.BackLogService;
import C043.GameVault.services.PlayingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class GameListController {

    @Autowired
    private BackLogService backLogService;

    @Autowired
    private PlayingService playingService;

    @Autowired
    private Validation validation;

    @PostMapping("/backlog")
    @ResponseStatus(HttpStatus.CREATED)
    public RespDTO postBacklogEntry(@AuthenticationPrincipal User user, @RequestBody @Validated GameDTO body, BindingResult validation) {
        this.validation.validate(validation);
        BackLog newBacklog = this.backLogService.postBackLog(body, user);
        return new RespDTO(newBacklog.getId());
    }

    @GetMapping("/backlog")
    public List<BackLog> getBackLogByUser(@AuthenticationPrincipal User user) {
        return this.backLogService.getBackLogByUser(user);
    }

    @DeleteMapping("/backlog/{backlogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBackLog(@AuthenticationPrincipal User user, @PathVariable int backlogId) {
        this.backLogService.deleteBackLog(user, backlogId);
    }

    @PostMapping("/playing")
    @ResponseStatus(HttpStatus.CREATED)
    public RespDTO postPlayingEntry(@AuthenticationPrincipal User user, @RequestBody @Validated GameDTO body, BindingResult validation) {
        this.validation.validate(validation);
        PlayingList newPlayingList = this.playingService.postPlaying(body, user);
        return new RespDTO(newPlayingList.getId());
    }

    @GetMapping("/playing")
    public List<PlayingList> getPlayingList(@AuthenticationPrincipal User user) {
        return this.playingService.getPlayingListByUser(user);
    }

    @DeleteMapping("/playing/{playingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaying(@AuthenticationPrincipal User user, @PathVariable int playingId) {
        this.playingService.deletePlayingList(user, playingId);
    }
}