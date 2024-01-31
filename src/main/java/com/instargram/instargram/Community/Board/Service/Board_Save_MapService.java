package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_Save_MapRepository;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberSaveGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Board_Save_MapService {
    private final Board_Save_MapRepository boardSaveMapRepository;
    private final MemberSaveGroupService memberSaveGroupService;
    public Board_Save_Map create(Board board, Member member, SaveGroup saveGroup) {
        Board_Save_Map boardSaveMap = new Board_Save_Map();
        boardSaveMap.setBoard(board);
        boardSaveMap.setMember(member);
        boardSaveMap.setSaveGroup(saveGroup);
        return this.boardSaveMapRepository.save(boardSaveMap);
    }

    public Board_Save_Map exists(Board board, Member member, SaveGroup saveGroup) {
        return this.boardSaveMapRepository.findByBoardAndMemberAndSaveGroup(board, member, saveGroup);
    }


    public void delete(Board board) {
        List<Board_Save_Map> boardSaveMaps = boardSaveMapRepository.findByBoard(board);
        boardSaveMapRepository.deleteAll(boardSaveMaps);
    }

    public List<Board_Save_Map> getSaveGroup(Member member) {
        List<Board_Save_Map> boardSaveMaps = this.boardSaveMapRepository.findByMember(member);
        return boardSaveMaps;
    }

    public List<Board_Save_Map> getALLSaveGroup(Member member) {
        return this.boardSaveMapRepository.findTop4ByMemberAndSaveGroupIsNullOrderByBoardCreateDateDesc(member);
    }

    public List<Board> getAllSavedBoard(Member member)
    {
        return getALLSaveGroup(member).stream().map(Board_Save_Map::getBoard).toList();
    }

    public Set<SaveGroup> getSaveGroupSet(Member member)
    {
        return this.boardSaveMapRepository.findByMember(member).stream().map(Board_Save_Map::getSaveGroup).collect(Collectors.toSet());
    }

    public List<Board> getSavedBoardBySaveGroup(SaveGroup saveGroup)
    {
        return this.boardSaveMapRepository.findTop1BySaveGroupOrderByIdDesc(saveGroup).stream().map(Board_Save_Map::getBoard).toList();
    }


    public Map<SaveGroup, List<Board>> getSavedBoardMapByMember(Member member) {

        Map<SaveGroup, List<Board>> savedBoardMap = new HashMap<>();

        List<SaveGroup> saveGroups = memberSaveGroupService.getSaveGroupByMember(member);

        for(SaveGroup group : saveGroups)
        {
            if(group != null)
            {
                savedBoardMap.put(group, getSavedBoardBySaveGroup(group));
            }
        }

        SaveGroup saveGroup = new SaveGroup();
        saveGroup.setName("모든 게시글");
        savedBoardMap.put(saveGroup, getAllSavedBoard(member));


        return savedBoardMap;
    }
}
