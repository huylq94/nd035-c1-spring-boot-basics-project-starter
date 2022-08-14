package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    public List<Note> getNotesByUserId(Integer userId) {

        return noteMapper.findNoteByUserId(userId);
    }

    public int insert(Note note) {

        return noteMapper.insert(note);
    }

    public void update(Note note) {
        noteMapper.update(note);
    }

    public boolean delete(Integer noteId) {

        return noteMapper.delete(noteId);
    }
}
