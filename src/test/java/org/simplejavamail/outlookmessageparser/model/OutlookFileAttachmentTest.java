/*
 * Copyright (C) ${project.inceptionYear} Benny Bottema (benny@bennybottema.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.simplejavamail.outlookmessageparser.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutlookFileAttachmentTest {
	
	@Test
	public void checkSmimeFilename() {
		testSmimeFilenameScenario(null, "image/png", null);
		testSmimeFilenameScenario("file", "image/png", "file");
		testSmimeFilenameScenario("file", "multipart/signed", "file");
		testSmimeFilenameScenario(null, "multipart/signed", "smime.p7s");
		testSmimeFilenameScenario(null, "multipart/signed;protocol=\"moomoo\"", null);
		testSmimeFilenameScenario("file", "multipart/signed;protocol=\"moomoo\"", "file");
		testSmimeFilenameScenario(null, "multipart/signed;protocol=\"application/pkcs7-signature\"", "smime.p7s");
		testSmimeFilenameScenario("file", "multipart/signed;protocol=\"application/pkcs7-signature\"", "file");
	}
	
	private void testSmimeFilenameScenario(String filename, String mimeTag, String expectedNewFilename) {
		OutlookFileAttachment subject = new OutlookFileAttachment();
		subject.setFilename(filename);
		subject.setMimeTag(mimeTag);
		
		subject.checkSmimeFilename();
		assertThat(subject.getFilename()).isEqualTo(expectedNewFilename);
	}
	
	@Test
	public void checkMimeTag() {
		testMimeTagScenario("image/png", "image.png", "image/png");
		testMimeTagScenario("image/png", "image.bmp", "image/png");
		testMimeTagScenario("image/png", null, "image/png");
		testMimeTagScenario(null, null, null);
		testMimeTagScenario(null, "moo", "application/octet-stream");
		testMimeTagScenario(null, "image.png", "image/png");
		testMimeTagScenario(null, "image.bmp", "image/bmp");
	}
	
	private void testMimeTagScenario(String mimeTag, String filename, String expectedNewMimeTag) {
		OutlookFileAttachment subject = new OutlookFileAttachment();
		subject.setMimeTag(mimeTag);
		subject.setFilename(filename);
		
		subject.checkMimeTag();
		assertThat(subject.getMimeTag()).isEqualTo(expectedNewMimeTag);
	}
}